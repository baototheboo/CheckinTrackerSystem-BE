package com.example.ctsbe.util;

import com.example.ctsbe.constant.ApplicationConstant;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public String convertLocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormat = date.format(formatter);
        return dateFormat;
    }

    public String convertLocalDateToMonthAndYear(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String dateFormat = date.format(formatter);
        return dateFormat;
    }

    public String convertInstantToStringYearMonthDay(Instant date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String dateFormat = formatter.format(date);
        return dateFormat.substring(0, 10);
    }

    public static String convertInstantToTimeVerifyString(Instant date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).format(formatter);
    }


    public static String convertTimeVerifyToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String dateFormat = date.atStartOfDay().format(formatter);
        return dateFormat;
    }

    public static String convertTimeVerifyToStringDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateFormat = dateTime.format(formatter);
        return dateFormat;
    }

    public static String convertTimeVerifyToStringTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String dateFormat = dateTime.format(formatter);
        return dateFormat;
    }

    public int getLengthOfMonth(String yearMonth) {
        YearMonth res = convertStringToYearMonth(yearMonth);
        return res.lengthOfMonth();
    }

    public YearMonth convertStringToYearMonth(String yearMonth) {
        String[] monthAndYear = yearMonth.split("-");
        int year = Integer.parseInt(monthAndYear[0]);
        int month = Integer.parseInt(monthAndYear[1]);
        YearMonth res = java.time.YearMonth.of(year, month);
        return res;
    }

    public boolean compareYearMonth(String yearMonth, YearMonth now) {
        YearMonth compare = convertStringToYearMonth(yearMonth);
        return compare.isAfter(now);
    }

    public boolean checkWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) return true;
        else return false;
    }

    public String convertLocalDateToStringDay(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        String dateFormat = date.format(formatter);
        return dateFormat;
    }

    public List<Integer> getListDayCheck(List<String> list) {
        List<Integer> res = new ArrayList<>();
        for (String s : list) {
            String[] statusNote =(s == null) ? null : s.split("-");
            String status = (s == null) ? null :  statusNote[0];
            String note = (s == null) ? null : statusNote[1];
            if (status != null && status.equalsIgnoreCase("ok")) res.add(1); //1 la ok
            else if (status != null && status.equalsIgnoreCase("late")) res.add(2); // 2 la late
            else if (status != null
                    && status.equalsIgnoreCase("absent")
                    && note.equalsIgnoreCase("Vắng"))
                res.add(3); // 3 la ko di lam
            else if (status != null
                    && status.equalsIgnoreCase("absent")
                    && (note.equalsIgnoreCase("Cuối tuần") || note.equalsIgnoreCase("Ngày nghỉ lễ")))
                res.add(4); // 4 la ngay nghi hoac cuoi tuan
            else if (status == null && note == null) res.add(5); // 5 la not yet
        }
        return res;
    }

    public static Instant convertLocalDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toInstant();
    }

    public Instant plusInstant(Instant instant) {
        return instant.plus(7, ChronoUnit.HOURS);
    }
}
