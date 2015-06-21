package cz.muni.fi.PB138.main.gui;

/**
 *
 * Created by Eva on 18.6.2015.
 */
public enum TimeIntervalType {
    DAY(0), WEEK(1), MONTH(2);

    private final int value;

    private TimeIntervalType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
