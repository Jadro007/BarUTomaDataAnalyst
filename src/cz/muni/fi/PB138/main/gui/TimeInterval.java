package cz.muni.fi.PB138.main.gui;

import java.time.LocalDate;

/**
 * Created by Eva on 18.6.2015.
 */
public class TimeInterval implements Comparable<TimeInterval> {

    private LocalDate from;
    private LocalDate to;

    public TimeInterval(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
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

    //TODO Change this
    @Override
    public String toString() {
        return from.toString() + " - " + to.toString();
    }

    @Override
    public int compareTo(TimeInterval timeInterval) {
        return this.from.compareTo(timeInterval.getFrom());
    }
}
