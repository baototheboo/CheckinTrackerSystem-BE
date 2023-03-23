package com.example.ctsbe.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

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
}
