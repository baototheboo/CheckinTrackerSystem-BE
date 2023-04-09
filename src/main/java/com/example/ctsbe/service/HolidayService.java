package com.example.ctsbe.service;

import com.example.ctsbe.entity.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {
    List<Holiday> getAllHoliday();

    boolean checkHoliday(LocalDate date);
}
