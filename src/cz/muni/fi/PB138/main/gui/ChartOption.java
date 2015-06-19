package cz.muni.fi.PB138.main.gui;

import java.util.List;

/**
 * Created by Eva on 2.6.2015.
 */
public class ChartOption {
    private String name;
    private String valueLabel;
    private String rangeLabel;
    private List<ChartType> chartTypes;
    private String graphParamName;
    private ChartData chartData;

    public ChartOption(String name, String valueLabel, String rangeLabel, List<ChartType> chartTypes,
                       String graphParamName, ChartData chartData) {
        this.name = name;
        this.chartTypes = chartTypes;
        this.graphParamName = graphParamName;
        this.chartData = chartData;
        this.valueLabel = valueLabel;
        this.rangeLabel = rangeLabel;
    }

    public String getName() {
        return name;
    }

    public String getValueLabel() {
        return valueLabel;
    }

    public String getRangeLabel() {
        return rangeLabel;
    }

    public List<ChartType> getChartTypes() {
        return chartTypes;
    }

    public String getGraphParamName() {
        return graphParamName;
    }

    public ChartData getChartData() {
        return chartData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChartOption that = (ChartOption) o;

        if (!name.equals(that.name)) return false;
        if (!valueLabel.equals(that.valueLabel)) return false;
        if (!rangeLabel.equals(that.rangeLabel)) return false;
        if (!chartTypes.equals(that.chartTypes)) return false;
        if (!graphParamName.equals(that.graphParamName)) return false;
        return chartData == that.chartData;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + valueLabel.hashCode();
        result = 31 * result + rangeLabel.hashCode();
        result = 31 * result + chartTypes.hashCode();
        result = 31 * result + graphParamName.hashCode();
        result = 31 * result + chartData.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
