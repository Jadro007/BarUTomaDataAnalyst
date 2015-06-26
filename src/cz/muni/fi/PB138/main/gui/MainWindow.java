package cz.muni.fi.PB138.main.gui;

import com.toedter.calendar.JDateChooser;
import cz.muni.fi.PB138.main.entities.Bar;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static cz.muni.fi.PB138.main.gui.LoadManager.getUserInformation;

/**
 * The class for main window of the app. Enables user to choose all options for data displayed in the charts.
 * Created by Eva on 21.5.2015.
 */
public class MainWindow {

    //constants for settings of the GUI
    private static final int MAX_SELECTED_BARS = 4;
    private static final int MAX_SELECTED_TIME_INTERVALS = 14;
    private static final int MAX_SELECTED_DISPLAY_LIMIT = 10;
    private static final int INITIAL_SELECTED_DISPLAY_LIMIT = 3;
    private static final int HORIZONTAL_PREFERRED_SIZE = 1150;
    private static final int VERTICAL_PREFERRED_SIZE = 600;

    //GUI components
    private Frame frame;
    private JComboBox chartOptionComboBox;
    private JComboBox chartTypeComboBox;
    private JSpinner graphParamSpinner;
    private JButton logOutButton;
    private JDateChooser toDateChooser;
    private JDateChooser fromDateChooser;
    private JLabel chartParamLabel;
    private JPanel panel;
    private JTable barsTable;
    private ChartPanel chartPanel;
    private JButton chartButton;
    private JScrollPane tableScrollPane;
    private JPanel tablePanel;
    private BarsTableModel barsTableModel;
    private TimeIntervalType lastSelectedInterval = TimeIntervalType.DAY;

    private Boolean isAdmin;

    /**
     * Method used for creation of options of customisation of the chart for user.
     * @return List<ChartOption> available for user
     */
    private List<ChartOption> getChartOptions() {
        List<ChartOption> chartOptions = new ArrayList<>();

        chartOptions.add(new ChartOption("My most drunk drinks", "Number of drinks", "Drink",
                Arrays.asList(ChartType.BAR, ChartType.PIE), "Drinks to display:", ChartData.DRUNK_DRINKS));
        chartOptions.add(new ChartOption("My payments", "Payment", "Time", Arrays.asList(ChartType.BAR, ChartType.XY), "", ChartData.PAYMENTS));

        if (isAdmin) {
            chartOptions.add(new ChartOption("Best selling drinks at bars", "Number of drinks", "Drink",
                    Arrays.asList(ChartType.BAR, ChartType.PIE), "Drinks to display:", ChartData.SELLING_DRINKS));
            chartOptions.add(new ChartOption("Pure alcohol sold at bars", "Amount",  "Time",
                    Arrays.asList(ChartType.BAR, ChartType.XY), "", ChartData.PURE_ALCOHOL_SOLD));
            chartOptions.add(new ChartOption("Earnings of bars", "Earning", "Time",
                    Arrays.asList(ChartType.BAR, ChartType.XY), "", ChartData.EARNINGS));
        }

        return chartOptions;
    }

    /**
     * Method returns list of chart data which are available only for user who is admin.
     * @return List<ChartData> available only for user who is admin
     */
    private static List<ChartData> getAdminsChartData() {
        return new ArrayList<>(Arrays.asList(ChartData.SELLING_DRINKS, ChartData.PURE_ALCOHOL_SOLD, ChartData.EARNINGS));
    }

    /**
     * Constructor of the class.
     * Initialises main window of the app and
     */
    public MainWindow() {
        chartOptionComboBox.addActionListener(e -> {
            setChartTypeComboBoxItems();
            setChartParamComponents();
        });
        logOutButton.addActionListener(e -> {
            LoginWindow.createLoginWindow();
            this.frame.setVisible(false);
            this.frame.dispose();
        });
        chartButton.addActionListener(e -> {
            if (fromDateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Date \"from\" is not selected.\nReselect the date and try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (toDateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Date \"to\" is not selected.\nReselect the date and try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (toDateChooser.getDate().compareTo(fromDateChooser.getDate()) < 0) {
                JOptionPane.showMessageDialog(null, "Selected date \"from\" is higher than selected date \"to\". \n " +
                        "Reselect the dates and try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (isAdmin) {
                int checkedBarsCount = barsTableModel.getSelectedBars().size();
                ChartOption selectedChartOption = (ChartOption) chartOptionComboBox.getSelectedItem();
                if (getAdminsChartData().contains(selectedChartOption.getChartData()) && checkedBarsCount > MAX_SELECTED_BARS) {
                    JOptionPane.showMessageDialog(null, "Too many bars are selected.\nDeselect some of them and try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (getAdminsChartData().contains(selectedChartOption.getChartData()) && checkedBarsCount == 0) {
                    JOptionPane.showMessageDialog(null, "No bars are selected.\nSelect some of them and try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            long datesDiff = TimeUtils.datesDifference(fromDateChooser.getDate(), toDateChooser.getDate());
            if ((datesDiff > MAX_SELECTED_TIME_INTERVALS && graphParamSpinner.getValue() == TimeIntervalType.DAY) ||
                    (datesDiff > MAX_SELECTED_TIME_INTERVALS * 7 && graphParamSpinner.getValue() == TimeIntervalType.WEEK)
                    ){
                JOptionPane.showMessageDialog(null, "Too many time intervals are selected.\n Please change the type of time" +
                                "interval or select lower range of dates.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            showChart();
        });
    }


    /**
     * Sets frame of the MainWindow.
     * @param frame of the MainWindow
     */
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    /**
     * Creates new JFrame with MainWindow.
     */
    public static void createMainWindow(){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("My bar statics");
            MainWindow window = new MainWindow();
            window.setFrame(frame);
            frame.setContentPane(window.panel);
            frame.setPreferredSize(new Dimension(HORIZONTAL_PREFERRED_SIZE, VERTICAL_PREFERRED_SIZE));

            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Method for initialisation of GUI components.
     */
    private void createUIComponents() {
        isAdmin = getUserInformation().isCurrentUserAdmin();
        List<ChartOption> chartOptions = getChartOptions();

        chartOptionComboBox = new JComboBox();
        chartOptions.forEach(chartOptionComboBox::addItem);

        chartTypeComboBox = new JComboBox();
        setChartTypeComboBoxItems();
        fromDateChooser = new JDateChooser();
        fromDateChooser.getJCalendar().setMaxSelectableDate(new Date());
        fromDateChooser.setLocale(Locale.ENGLISH);

        toDateChooser = new JDateChooser();
        toDateChooser.getJCalendar().setMaxSelectableDate(new Date());
        toDateChooser.setLocale(Locale.ENGLISH);

        chartParamLabel = new JLabel();
        graphParamSpinner = new JSpinner();
        setChartParamComponents();

        tablePanel = new JPanel();
        tableScrollPane = new JScrollPane();
        if (isAdmin) {
            barsTableModel = new BarsTableModel();
            barsTable = new JTable(barsTableModel);
            barsTable.removeColumn(barsTable.getColumnModel().getColumn(0));
        } else {
            barsTable = null;
            tableScrollPane.setVisible(false);
            tablePanel.setVisible(false);
        }

        chartPanel = new ChartPanel(null);
        chartPanel.setMinimumDrawWidth(0);
        chartPanel.setMinimumDrawHeight(0);
        chartPanel.setMaximumDrawWidth(1920);
        chartPanel.setMaximumDrawHeight(1200);
    }

    /**
     * Method for changing graphParam label and spinner according to selected chart data type.
     */
    private void setChartParamComponents() {
        if (graphParamSpinner.getModel().getClass() != SpinnerNumberModel.class) {
            lastSelectedInterval = (TimeIntervalType) graphParamSpinner.getValue();
        }

        ChartOption option = (ChartOption) chartOptionComboBox.getSelectedItem();

        if (option.getChartParamName() != null && option.getChartParamName().length() != 0) {
            graphParamSpinner.setModel(new SpinnerNumberModel(INITIAL_SELECTED_DISPLAY_LIMIT, 1, MAX_SELECTED_DISPLAY_LIMIT, 1));
            chartParamLabel.setText(option.getChartParamName());
        } else {
            TimeIntervalType[] timeIntervalTypes = {TimeIntervalType.DAY, TimeIntervalType.WEEK, TimeIntervalType.MONTH};
            SpinnerListModel spinnerListModel = new SpinnerListModel(timeIntervalTypes);
            graphParamSpinner.setModel(spinnerListModel);
            graphParamSpinner.setValue(lastSelectedInterval);
            chartParamLabel.setText("Time interval:");
        }

        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)graphParamSpinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
    }

    /**
     * Method for changing chart type combo box according to selected chart data type.
     */
    private void setChartTypeComboBoxItems() {
        chartTypeComboBox.removeAllItems();
        ChartOption option = (ChartOption) chartOptionComboBox.getSelectedItem();
        option.getChartTypes().forEach(chartTypeComboBox::addItem);
    }

    /**
     * Method creates chart from options selected by user.
     */
    private void showChart() {
        ChartOption chartOption = (ChartOption) chartOptionComboBox.getSelectedItem();
        int displayLimit;
        if (chartOption.getChartParamName() == null || chartOption.getChartParamName().length() == 0) {
            TimeIntervalType timeIntervalType = (TimeIntervalType) graphParamSpinner.getValue();
            displayLimit = timeIntervalType.getValue();
        } else {
            displayLimit = (int) graphParamSpinner.getValue();
        }
        List<Bar> barList;
        if (isAdmin) {
            barList = barsTableModel.getSelectedBars();
        } else {
            barList = null;
        }
        ChartWorker chartWorker = new ChartWorker(
                chartOption,
                (ChartType) chartTypeComboBox.getSelectedItem(),
                barList,
                fromDateChooser.getDate(),
                toDateChooser.getDate(),
                displayLimit,
                chartPanel
        );
        chartWorker.execute();
    }

}
