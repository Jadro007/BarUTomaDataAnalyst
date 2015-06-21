package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.entities.Bar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.TableOrder;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

import static cz.muni.fi.PB138.main.gui.TimeIntervalType.DAY;
import static cz.muni.fi.PB138.main.gui.TimeIntervalType.WEEK;

/**
 * Created by Eva on 2.6.2015.
 */

//TODO create Worker from this class
public class ChartWorker extends SwingWorker<Void, Integer> {
    private ChartOption option;
    private ChartType type;
    private List<Bar> barList;
    private Date from;
    private Date to;
    private int displayLimit;
    private ChartPanel chartPanel;

    public ChartWorker(ChartOption option, ChartType type, List<Bar> barList, Date from, Date to, int displayLimit,
        ChartPanel chartPanel) {
        this.option = option;
        this.type = type;
        this.barList = barList;
        this.from = from;
        this.to = to;
        this.displayLimit = displayLimit;
        this.chartPanel = chartPanel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        chartPanel.setChart(createChart());
        return null;
    }

    private JFreeChart createChart() {
        CategoryDataset dataset = createDataset();
        JFreeChart chart;
        //TODO payments show legend set false
        switch (type) {
            case BAR:
                chart = ChartFactory.createBarChart(
                        option.getName(),
                        option.getValueLabel(),
                        option.getRangeLabel(),
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                break;
            case PIE:
                chart = ChartFactory.createMultiplePieChart(
                        option.getName(),
                        dataset,
                        TableOrder.BY_COLUMN,
                        true, true, false
                );
                break;
            case XY:
                chart = ChartFactory.createLineChart(
                        option.getName(),
                        option.getRangeLabel(),
                        option.getValueLabel(),
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false
                );
                break;
            default:
                return null;
        }
        return chart;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        List<TimeInterval> timeIntervalList;
        int i;
        switch(option.getChartData()) {
            case DRUNK_DRINKS:
                Map<String, Integer> drunkDrinksMap = LoadManager.getLoadUser().getMostPurchasedDrinks(TimeUtils.
                                utilDateToLocalDate(from), TimeUtils.utilDateToLocalDate(to));
                i = 0;
                for (Map.Entry<String, Integer> e : drunkDrinksMap.entrySet()) {
                    categoryDataset.setValue(e.getValue(), e.getKey(), "Drinks");
                    i++;
                    if (i == displayLimit) break;
                }
                return categoryDataset;
            case PAYMENTS:
                timeIntervalList = getTimeIntervalList(displayLimit, from, to);
                BigDecimal payment;
                for (TimeInterval ti : timeIntervalList) {
                    payment = LoadManager.getLoadUser().getPayment(ti.getFrom(), ti.getTo());
                    categoryDataset.setValue(payment, "Payments", ti);
                }
                return categoryDataset;
            case SELLING_DRINKS:
                Map<String, Integer> sellingDrinksMap;
                for (Bar b : barList) {
                    sellingDrinksMap = LoadManager.getLoadAdmin().getMostSoldDrinks(TimeUtils.utilDateToLocalDate(from),
                            TimeUtils.utilDateToLocalDate(to), b.getId());
                    i = 0;
                    for (Map.Entry<String, Integer> e : sellingDrinksMap.entrySet()) {
                        categoryDataset.setValue(e.getValue(), e.getKey(), b.getName());
                        i++;
                        if (i == displayLimit) break;
                    }
                }
                return categoryDataset;
            case USED_INGREDIENTS:
                Map<String, Double> ingredientsMap;
                for (Bar b : barList) {
                    ingredientsMap = LoadManager.getLoadAdmin().getMostUsedIngredients(TimeUtils.utilDateToLocalDate(from),
                            TimeUtils.utilDateToLocalDate(to), b.getId());
                    i = 0;
                    for (Map.Entry<String, Double> e : ingredientsMap.entrySet()) {
                        categoryDataset.setValue(e.getValue(), e.getKey(), b.getName());
                        i++;
                        if (i == displayLimit) break;
                    }
                }
                return categoryDataset;
            case PURE_ALCOHOL_SOLD:
                timeIntervalList = getTimeIntervalList(displayLimit, from, to);
                Map<String, Double> alcoholMap = new HashMap<>();
                for (Bar b : barList) {
                    alcoholMap.clear();
                    for (TimeInterval ti : timeIntervalList) {
                        alcoholMap.put(ti.toString(), LoadManager.getLoadAdmin().getAlcohol(ti.getFrom(),
                                ti.getTo(), b.getId()));
                    }
                    for (Map.Entry<String, Double> e : alcoholMap.entrySet()) {
                        categoryDataset.setValue(e.getValue(), b.getName(), e.getKey());
                    }
                }
                return categoryDataset;
            case EARNINGS:
                timeIntervalList = getTimeIntervalList(displayLimit, from, to);
                Map<String, BigDecimal> incomeMap = new HashMap<>();
                for (Bar b : barList) {
                    incomeMap.clear();
                    for (TimeInterval ti : timeIntervalList) {
                        incomeMap.put(ti.toString(), LoadManager.getLoadAdmin().getIncome(ti.getFrom(),
                                ti.getTo(), b.getId()));
                    }
                    for (Map.Entry<String, BigDecimal> e : incomeMap.entrySet()) {
                        categoryDataset.setValue(e.getValue(), b.getName(), e.getKey());
                    }
                }
                return categoryDataset;
        }
        return null;
    }

    private static List<TimeInterval> getTimeIntervalList(int displayLimit, Date from, Date to) {
        List<TimeInterval> timeIntervalList;
        if (DAY.getValue() == displayLimit) {
            timeIntervalList = TimeUtils.getDayIntervals(from, to);
        } else if (WEEK.getValue() == displayLimit) {
            timeIntervalList = TimeUtils.getWeekIntervals(from, to);
        } else {
            timeIntervalList = TimeUtils.getMonthIntervals(from, to);
        }
        return Collections.unmodifiableList(timeIntervalList);
    }

}
