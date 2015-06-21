package cz.muni.fi.PB138.main.gui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by Eva on 10.6.2015.
 */
public final class TimeUtils {

    static LocalDate utilDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    static Date localDateToUtilDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

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
        return timeIntervalList;
    }

    static List<TimeInterval> getWeekIntervals(Date from, Date to) {
        return getWeekIntervals(utilDateToLocalDate(from), utilDateToLocalDate(to));
    }

    static List<TimeInterval> getDayIntervals(LocalDate from, LocalDate to) {
        List<TimeInterval> timeIntervalList = new ArrayList<>();
        while(from.compareTo(to) <= 0) {
            timeIntervalList.add(new TimeInterval(from, from));
            from = from.plusDays(1);
        }
        return timeIntervalList;
    }

    static List<TimeInterval> getDayIntervals(Date from, Date to) {
        return getDayIntervals(utilDateToLocalDate(from), utilDateToLocalDate(to));
    }

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

    static List<TimeInterval> getMonthIntervals(Date from, Date to) {
        return getMonthIntervals(utilDateToLocalDate(from), utilDateToLocalDate(to));
    }

    static long datesDifference(Date from, Date to) {
        long diff = from.getTime() - to.getTime();
        return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
}
