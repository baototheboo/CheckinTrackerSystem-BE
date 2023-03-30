package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.repository.TimesheetRepository;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {
    @Autowired
    private TimesheetRepository timesheetRepository;

    DateUtil dateUtil = new DateUtil();

    @Override
    public List<Timesheet> getTimesheetByStaffIdAndMonth(int staffId, String month) {
        return timesheetRepository.getListTimesheetByStaffIdAndMonth(staffId, month);
    }

    @Override
    public TimesheetDTO checkDayStatus(List<Timesheet> list,int staffId) {
        //int daysOfMonth = dateUtil.getLengthOfMonth(month);
        //List<Integer> res = new ArrayList<>();
        //List<Integer> listDate = getListDayOfLocalDate(list);
        TimesheetDTO dto = new TimesheetDTO();
        List<String> listStatus = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            listStatus.add(list.get(i).getDateStatus());
        }
        List<Integer>  listDayCheck = dateUtil.getListDayCheck(listStatus);
        dto.setStaffId(staffId);
        dto.setDayCheck(listDayCheck);
        return dto;
    }


    @Override
    public List<Integer> getListDayOfLocalDate(List<Timesheet> list) {
        List<Integer> dayList = new ArrayList<>();
        for (Timesheet timesheet : list) {
            dayList.add(
                    Integer.parseInt(
                            dateUtil.convertLocalDateToStringDay(timesheet.getDate())
                    )
            );
        }
        return dayList;
    }
}
