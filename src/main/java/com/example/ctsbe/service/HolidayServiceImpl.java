package com.example.ctsbe.service;

import com.example.ctsbe.entity.Holiday;
import com.example.ctsbe.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;
    @Override
    public List<Holiday> getAllHoliday() {
        return holidayRepository.findAll();
    }
}
