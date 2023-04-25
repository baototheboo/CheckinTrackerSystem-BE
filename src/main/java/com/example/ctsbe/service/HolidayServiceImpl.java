package com.example.ctsbe.service;

import com.example.ctsbe.entity.Holiday;
import com.example.ctsbe.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;
    @Override
    public List<Holiday> getAllHoliday() {
        return holidayRepository.findAll();
    }

    @Override
    public boolean checkHoliday(LocalDate date) {
        List<Holiday> holidays = getAllHoliday();
        List<LocalDate> listDates = holidays.stream().map(Holiday::getDate).collect(Collectors.toList());
        return listDates.stream().anyMatch(date::equals);
    }
}
