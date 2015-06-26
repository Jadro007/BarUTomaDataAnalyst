package cz.muni.fi.PB138.main.gui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Utility class.
 * Provides conversion between date classes used in the project.
 * Provides division of time to regular time intervals.
 * Provides calculation of days between two dates.
 * Created by Eva on 10.6.2015.
 */
public abstract class TimeUtils {

    /**
     * Conversion of java.util.Date to java.time.LocalDate
     * @param date java.util.date
     * @return same java.time.LocalDate
     */
    static LocalDate utilDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Conversion of java.time.LocalDate to java.util.Date
     * @param date java.time.LocalDate
     * @return same date java.util.date
     */
    static Date localDateToUtilDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Divides time to regular weekly time intervals. If count of the days is not dividable by 7, rest of the days
     * is in the last time interval.
     * @param from starting date of time
     * @param to ending date of time
     * @return List<TimeInterval> of the time
     */
    static List<TimeInterval> getWeekIntervals(LocalDate from, LocalDate to) {
        LocalDate start = from;
        LocalDate end = from.plusWeeks(1).minusDays(1);
        List<TimeInterval> timeIntervalList = new ArrayList<>();
        while(end.compareTo(to) < 0) {
            timeIntervalList.add(new TimeInterval(start, end));
            start = start.plusWeeks(1);
            end = end.plusWeeks(1);
        }
        timeIntervalList.add(new TimeInterval(start, to));
        return Collections.unmodifiableList(timeIntervalList);
    }

    /**
     * Divides time to regular weekly time intervals. If count of the days is not dividable by 7, rest of the days
     * is in the last time interval.
     * @param from starting date of time
     * @param to ending date of time
     * @return List<TimeInterval> of the time divided in weeks
     */
    static List<TimeInterval> getWeekIntervals(Date from, Date to) {
        return getWeekIntervals(utilDateToLocalDate(from), utilDateToLocalDate(to));
    }

    /**
     * Divides time in daily regular time intervals.
     * @param from starting date of time
     * @param to ending date of time
     * @return List<TimeInterval> of the time divided in days
     */
    static List<TimeInterval> getDayIntervals(LocalDate from, LocalDate to) {
        List<TimeInterval> timeIntervalList = new ArrayList<>();
        while(from.compareTo(to) <= 0) {
            timeIntervalList.add(new TimeInterval(from, from));
            from = from.plusDays(1);
        }
        return timeIntervalList;
    }

    /**
     * Divides time in daily regular time intervals.
     * @param from starting date of time
     * @param to ending date of time
     * @return List<TimeInterval> of the time divided in days
     */
    static List<TimeInterval> getDayIntervals(Date from, Date to) {
        return getDayIntervals(utilDateToLocalDate(from), utilDateToLocalDate(to));
    }

    /**
     * Divides time in the monthly intervals. Division starts from the first date, so if i.e. starting date is 20th of
     * March, ending of the first time interval will be 19th of April and next time interval will be starting with 20th
     * of April. If ending date is sooner than ending of the last time interval, ending date is used.
     * @param from starting date of time
     * @param to ending date of time
     * @return List<TimeInterval> of the time divided in months
     */
    static List<TimeInterval> getMonthIntervals(LocalDate from, LocalDate to) {
        LocalDate start = from;
        LocalDate end = from.plusMonths(1).minusDays(1);
        List<TimeInterval> timeIntervalList = new ArrayList<>();
        while(end.compareTo(to) < 0) {
            timeIntervalList.add(new TimeInterval(start, end));
            start = end.plusDays(1);
            end = end.plusMonths(1);
        }
        timeIntervalList.add(new TimeInterval(start, to));
        return timeIntervalList;
    }

    /**
     * Divides time in the monthly intervals. Division starts from the first date, so if i.e. starting date is 20th of
     * March, ending of the first time interval will be 19th of April and next time interval will be starting with 20th
     * of April. If ending date is sooner than ending of the last time interval, ending date is used.
     * @param from starting date of time
     * @param to ending date of time
     * @return List<TimeInterval> of the time divided in months
     */
    static List<TimeInterval> getMonthIntervals(Date from, Date to) {
        return getMonthIntervals(utilDateToLocalDate(from), utilDateToLocalDate(to));
    }

    /**
     * Calculates difference between two Java.util.Date -s
     * @param from starting date of time
     * @param to ending date of time
     * @return long number of days between two dates
     */
    static long datesDifference(Date from, Date to) {
        long diff = from.getTime() - to.getTime();
        return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
}
