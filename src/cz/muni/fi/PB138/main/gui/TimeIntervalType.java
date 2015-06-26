package cz.muni.fi.PB138.main.gui;

/**
 * Enum of basic types of regular time intervals used in the project.
 * Created by Eva on 18.6.2015.
 */
public enum TimeIntervalType {
    DAY(0), WEEK(1), MONTH(2);

    //enum value
    private final int value;

    /**
     * Constructor of the class.
     * @param value of the enum object
     */
    TimeIntervalType(int value) {
        this.value = value;
    }

    /**
     * Returns value of the enum object.
     * @return value of the enum object
     */
    public int getValue() {
        return value;
    }
}
