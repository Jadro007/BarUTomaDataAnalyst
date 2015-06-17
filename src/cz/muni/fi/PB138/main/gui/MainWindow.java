package cz.muni.fi.PB138.main.gui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Eva on 21.5.2015.
 */
//TODO graphs
public class MainWindow {
    private Frame frame;
    private JComboBox dataComboBox;
    private JComboBox<String> chartComboBox;
    private JSpinner graphParamSpinner;
    private JButton logOutButton;
    private JDateChooser toDateChooser;
    private JDateChooser fromDateChooser;
    private JLabel graphParamLabel;
    private JPanel panel;
    private JTable barsTable;

    private static List<ChartOption> chartOptions = getChartOptions();

    public MainWindow() {
        dataComboBox.addActionListener(e -> {
            setChartComboBoxItems();
            setChartParamComponents();
        });
        logOutButton.addActionListener(e -> {
            LoginWindow.createLoginWindow();
            this.frame.setVisible(false);
            this.frame.dispose();
        });
    }

    public static void main(String[] args) {
        createMainWindow();
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
        dataComboBox = new JComboBox();
        chartOptions.forEach(dataComboBox::addItem);
        dataComboBox.setSelectedItem(0);

        chartComboBox = new JComboBox();
        setChartComboBoxItems();

        //TODO fromDateChooser & toDateChooser min date restriction
        //TODO solve from greater than to
        fromDateChooser = new JDateChooser();
        fromDateChooser.getJCalendar().setMaxSelectableDate(new Date());

        toDateChooser = new JDateChooser();
        toDateChooser.getJCalendar().setMaxSelectableDate(new Date());

        graphParamLabel = new JLabel();
        graphParamSpinner = new JSpinner();
        setChartParamComponents();

        BarsTableModel barsTableModel = new BarsTableModel();
        barsTable = new JTable(barsTableModel);
        barsTable.removeColumn(barsTable.getColumnModel().getColumn(0));
        //TODO ChartPanel
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    private void setChartParamComponents() {
        ChartOption option = (ChartOption)dataComboBox.getSelectedItem();

        if (option.graphParamName == null || option.graphParamName.length() != 0) {
            graphParamSpinner.setVisible(true);
            graphParamLabel.setVisible(true);
            //TODO avg initial value? min & max restriction
            graphParamSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
            graphParamLabel.setText(option.graphParamName);
        } else {
            graphParamSpinner.setVisible(false);
            graphParamLabel.setVisible(false);
        }
    }

    private void setChartComboBoxItems() {
        chartComboBox.removeAllItems();
        ChartOption option = (ChartOption) dataComboBox.getSelectedItem();
        if (option.chartTypes.contains(ChartType.PIE)) {
            chartComboBox.addItem("Pie chart");
        }
        if (option.chartTypes.contains(ChartType.BAR)) {
            chartComboBox.addItem("Bar chart");
        }
        if (option.chartTypes.contains(ChartType.XY)) {
            chartComboBox.addItem("XY chart");
        }
    }

    private static List<ChartOption> getChartOptions() {
        List<ChartOption> chartOptions = new ArrayList<>();

        chartOptions.add(new ChartOption("My most drunk drinks", Arrays.asList(ChartType.BAR, ChartType.PIE), "Drinks to display:"));
        chartOptions.add(new ChartOption("My payments", Arrays.asList(ChartType.BAR, ChartType.XY), ""));

        //TODO worker
        if (LoadManager.getUserInformation().isCurrentUserAdmin()) {
            chartOptions.add(new ChartOption("Best selling drinks at bars", Arrays.asList(ChartType.BAR, ChartType.PIE),
                    "Drinks to display:"));
            chartOptions.add(new ChartOption("Most used ingredients at bars", Arrays.asList(ChartType.BAR, ChartType.PIE),
                    "Ingredients to display:"));
            chartOptions.add(new ChartOption("Pure alcohol sold at bars", Arrays.asList(ChartType.BAR, ChartType.XY), ""));
            chartOptions.add(new ChartOption("Earnings of bars", Arrays.asList(ChartType.BAR, ChartType.XY), ""));
        }

        return chartOptions;
    }

    private static class ChartOption {
        String name;
        List<ChartType> chartTypes;
        String graphParamName;

        public ChartOption(String name, List<ChartType> chartTypes, String graphParamName) {
            this.name = name;
            this.chartTypes = chartTypes;
            this.graphParamName = graphParamName;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private enum ChartType {
        BAR, XY, PIE
    }
}
