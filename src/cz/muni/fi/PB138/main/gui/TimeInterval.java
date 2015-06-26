package cz.muni.fi.PB138.main.gui;

import java.time.LocalDate;

/**
 * Class defines time interval.
 * Created by Eva on 18.6.2015.
 */
public class TimeInterval implements Comparable<TimeInterval> {

    //start of time interval
    private LocalDate from;
    //end of time interval
    private LocalDate to;

    /**
     * Constructor of the class.
     * @param from start of time interval
     * @param to end of time interval
     */
    public TimeInterval(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Returns starting date of the time interval.
     * @return LocalDate start of time interval
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns ending date of the time interval.
     * @return Local date end of time interval.
     */
    public LocalDate getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeInterval that = (TimeInterval) o;

        return from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if (to.equals(from)) {
            return from.getDayOfMonth() + "." + from.getMonthValue() + ".";
        }
        return from.getDayOfMonth() + "." + from.getMonthValue() + "." + from.getYear() + "-" +
                to.getDayOfMonth() + "." + to.getMonthValue() + "." + to.getYear();
    }

    @Override
    public int compareTo(TimeInterval timeInterval) {
        return this.from.compareTo(timeInterval.getFrom());
    }
}
