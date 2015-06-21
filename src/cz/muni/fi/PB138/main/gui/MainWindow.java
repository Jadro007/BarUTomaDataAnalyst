package cz.muni.fi.PB138.main.gui;

import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cz.muni.fi.PB138.main.gui.LoadManager.getUserInformation;

/**
 * Created by Eva on 21.5.2015.
 */
//TODO correct resizing
//TODO visual loading of chart
public class MainWindow {
    private static final int MAX_SELECTED_BARS = 4;
    private static final int MAX_SELECTED_TIME_INTERVALS = 20;
    private static final int MAX_SELECTED_DISPLAY_LIMIT = 10;
    private static final int INITIAL_SELECTED_DISPLAY_LIMIT = 3;

    private Frame frame;
    private JComboBox chartOptionComboBox;
    private JComboBox chartTypeComboBox;
    private JSpinner graphParamSpinner;
    private JButton logOutButton;
    private JDateChooser toDateChooser;
    private JDateChooser fromDateChooser;
    private JLabel graphParamLabel;
    private JPanel panel;
    private JTable barsTable;
    private ChartPanel chartPanel;
    private JButton chartButton;
    private BarsTableModel barsTableModel;
    private Boolean isAdmin;

    private List<ChartOption> getChartOptions() {
        List<ChartOption> chartOptions = new ArrayList<>();

        chartOptions.add(new ChartOption("My most drunk drinks", "Number of drinks", "Drink",
                Arrays.asList(ChartType.BAR, ChartType.PIE), "Drinks to display:", ChartData.DRUNK_DRINKS));
        chartOptions.add(new ChartOption("My payments", "Payment", "Time", Arrays.asList(ChartType.BAR, ChartType.XY), "", ChartData.PAYMENTS));

        if (isAdmin) {
            chartOptions.add(new ChartOption("Best selling drinks at bars", "Number of drinks", "Drink",
                    Arrays.asList(ChartType.BAR, ChartType.PIE), "Drinks to display:", ChartData.SELLING_DRINKS));
            chartOptions.add(new ChartOption("Most used ingredients at bars", "Amount", "Ingredient",
                    Arrays.asList(ChartType.BAR, ChartType.PIE), "Ingredients to display:", ChartData.USED_INGREDIENTS));
            chartOptions.add(new ChartOption("Pure alcohol sold at bars", "Amount",  "Time",
                    Arrays.asList(ChartType.BAR, ChartType.XY), "", ChartData.PURE_ALCOHOL_SOLD));
            chartOptions.add(new ChartOption("Earnings of bars", "Earning", "Time",
                    Arrays.asList(ChartType.BAR, ChartType.XY), "", ChartData.EARNINGS));
        }

        return chartOptions;
    }

    private static List<ChartData> getAdminsOptions() {
        return new ArrayList<>(Arrays.asList(ChartData.SELLING_DRINKS, ChartData.USED_INGREDIENTS, ChartData.PURE_ALCOHOL_SOLD,
                ChartData.EARNINGS));
    }

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
            int checkedBarsCount = barsTableModel.getChckedBarList().size();
            ChartOption selectedChartOption = (ChartOption) chartOptionComboBox.getSelectedItem();
            if (getAdminsOptions().contains(selectedChartOption.getChartData()) && checkedBarsCount > MAX_SELECTED_BARS) {
                JOptionPane.showMessageDialog(null, "Too many bars are selected.\nDeselect some of them and try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
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

    public static void main(String[] args) {
        createMainWindow();
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public static void createMainWindow(){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("My bar statics");
            MainWindow window = new MainWindow();
            window.setFrame(frame);
            frame.setContentPane(window.panel);
            frame.setPreferredSize(new Dimension(1150, 600));

            frame.pack();
            frame.setVisible(true);
        });
    }

    private void createUIComponents() {
        //TODO Worker??
        isAdmin = getUserInformation().isCurrentUserAdmin();
        List<ChartOption> chartOptions = getChartOptions();

        chartOptionComboBox = new JComboBox();
        chartOptions.forEach(chartOptionComboBox::addItem);
        chartOptionComboBox.setSelectedItem(0);

        chartTypeComboBox = new JComboBox();
        setChartTypeComboBoxItems();

        //TODO fromDateChooser & toDateChooser min date restriction
        fromDateChooser = new JDateChooser();
        fromDateChooser.getJCalendar().setMaxSelectableDate(new Date());

        toDateChooser = new JDateChooser();
        toDateChooser.getJCalendar().setMaxSelectableDate(new Date());

        graphParamLabel = new JLabel();
        graphParamSpinner = new JSpinner();
        setChartParamComponents();

        if (this.isAdmin) {
            barsTableModel = new BarsTableModel();
            barsTable = new JTable(barsTableModel);
            barsTable.removeColumn(barsTable.getColumnModel().getColumn(0));
        } else {
            barsTable = null;
        }

        chartPanel = new ChartPanel(null);
    }

    private void setChartParamComponents() {
        ChartOption option = (ChartOption) chartOptionComboBox.getSelectedItem();

        if (option.getGraphParamName() != null && option.getGraphParamName().length() != 0) {
            graphParamSpinner.setModel(new SpinnerNumberModel(INITIAL_SELECTED_DISPLAY_LIMIT, 1, MAX_SELECTED_DISPLAY_LIMIT, 1));
            graphParamLabel.setText(option.getGraphParamName());
        } else {
            TimeIntervalType[] timeIntervalTypes = {TimeIntervalType.DAY, TimeIntervalType.WEEK, TimeIntervalType.MONTH};
            SpinnerListModel spinnerListModel = new SpinnerListModel(timeIntervalTypes);
            graphParamSpinner.setModel(spinnerListModel);
            graphParamLabel.setText("Time interval:");
        }
    }

    private void setChartTypeComboBoxItems() {
        chartTypeComboBox.removeAllItems();
        ChartOption option = (ChartOption) chartOptionComboBox.getSelectedItem();
        option.getChartTypes().forEach(chartTypeComboBox::addItem);
    }

    private void showChart() {
        ChartOption chartOption = (ChartOption) chartOptionComboBox.getSelectedItem();
        int displayLimit;
        if (chartOption.getGraphParamName() == null || chartOption.getGraphParamName().length() == 0) {
            TimeIntervalType timeIntervalType = (TimeIntervalType) graphParamSpinner.getValue();
            displayLimit = timeIntervalType.getValue();
        } else {
            displayLimit = (int) graphParamSpinner.getValue();
        }
        ChartWorker chartWorker = new ChartWorker(
                chartOption,
                (ChartType) chartTypeComboBox.getSelectedItem(),
                barsTableModel.getChckedBarList(),
                fromDateChooser.getDate(),
                toDateChooser.getDate(),
                displayLimit,
                chartPanel
        );
        chartWorker.execute();
    }

}
