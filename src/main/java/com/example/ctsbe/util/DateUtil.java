package com.example.ctsbe.util;

import com.example.ctsbe.constant.ApplicationConstant;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);
        return localDate;
    }

    public String convertLocalDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormat = date.format(formatter);
        return dateFormat;
    }

    public String convertLocalDateToMonthAndYear(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String dateFormat = date.format(formatter);
        return dateFormat;
    }

    public String convertInstantToStringYearMonthDay(Instant date){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String dateFormat = formatter.format(date);
        return dateFormat.substring(0,10);
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

    public int getLengthOfMonth(LocalDate YearMonthDay){
        String convert = convertLocalDateToMonthAndYear(YearMonthDay);
        String []monthAndYear = convert.split("-");
        int year = Integer.parseInt(monthAndYear[0]);
        int month = Integer.parseInt(monthAndYear[1]);
        YearMonth res = YearMonth.of(year,month);
        return res.lengthOfMonth();
    }
     public static Instant convertLocalDateTimeToInstant(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toInstant();
     }
}
