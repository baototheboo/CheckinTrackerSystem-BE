package com.example.ctsbe.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public int getLengthOfMonth(String YearMonth){
        //String convert = convertLocalDateToMonthAndYear(YearMonthDay);
        String []monthAndYear = YearMonth.split("-");
        int year = Integer.parseInt(monthAndYear[0]);
        int month = Integer.parseInt(monthAndYear[1]);
        YearMonth res = java.time.YearMonth.of(year,month);
        return res.lengthOfMonth();
    }

    public String convertLocalDateToStringDay(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        String dateFormat = date.format(formatter);
        return dateFormat;
    }

    public List<Integer> getListDayCheck(List<String> list){
        List<Integer> res = new ArrayList<>();
        for (String s: list) {
            if(s.equalsIgnoreCase("ok")) res.add(1); //1 la ok
            else if(s.equalsIgnoreCase("late")) res.add(2); // 2 la late
        }
        return res;
    }


}
