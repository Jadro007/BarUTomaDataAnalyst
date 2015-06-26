package cz.muni.fi.PB138.main.gui;

import java.util.List;
/**
 * Object of the class possesses information selected by user needed for creation of chart.
 * Created by Eva on 2.6.2015.
 */
public class ChartOption {
    //name of the option, which will displayed to user
    private String name;
    //name of value displayed in chart (of x axis)
    private String valueLabel;
    //name of range displayed in chart (of y axis)
    private String rangeLabel;
    //list of types of charts available for user's selection
    private List<ChartType> chartTypes;
    //name of parameter customizable for user (if null, than time interval is used as parameter)
    private String graphParamName;
    //data which will be displayed to user (used for easier customization of the code)
    private ChartData chartData;

    /**
     * Constructor of the object.
     * @param name of the option, which will displayed to user
     * @param valueLabel of value displayed in chart (of x axis)
     * @param rangeLabel of range displayed in chart (of y axis)
     * @param chartTypes list of types of charts available for user's selection
     * @param chartParamName name of parameter customizable for user (if null, than time interval is used as parameter)
     * @param chartData which will be displayed to user (used for easier customization of the code)
     */
    public ChartOption(String name, String valueLabel, String rangeLabel, List<ChartType> chartTypes,
                       String chartParamName, ChartData chartData) {
        this.name = name;
        this.chartTypes = chartTypes;
        this.graphParamName = chartParamName;
        this.chartData = chartData;
        this.valueLabel = valueLabel;
        this.rangeLabel = rangeLabel;
    }

    /**
     * Returns name of ChartOption, which will displayed to user.
     * @return String name of ChartOption
     */
    public String getName() {
        return name;
    }

    /**
     * Returns valueLabel of value displayed in chart (of x axis).
     * @return String label of chart value (x axis)
     */
    public String getValueLabel() {
        return valueLabel;
    }

    /**
     * Returns rangeLabel of range displayed in chart (of y axis).
     * @return String label of chart range (y axis)
     */
    public String getRangeLabel() {
        return rangeLabel;
    }

    /**
     * Returns chartTypes list of types of charts available for user's selection.
     * @return List<ChartTypes> available for user's selection
     */
    public List<ChartType> getChartTypes() {
        return chartTypes;
    }

    /**
     * Returns chartParamName name of parameter customizable for user (if null, than time interval is used as parameter).
     * @return String name of parameter for chart
     */
    public String getChartParamName() {
        return graphParamName;
    }

    /**
     * Returns chartData which will be displayed to user (used for easier customization of the code).
     * @return ChartData of chart
     */
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
