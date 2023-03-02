package com.example.ctsbe.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public Instant convertStringToInstant(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return instant;
    }
}
