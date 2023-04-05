package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.repository.TimesheetRepository;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {
    @Autowired
    private TimesheetRepository timesheetRepository;
    @Autowired
    private StaffRepository staffRepository;

    DateUtil dateUtil = new DateUtil();

    @Override
    public List<Timesheet> getTimesheetByStaffIdAndMonth(int staffId, String month) {
        List<Timesheet> list = timesheetRepository.getListTimesheetByStaffIdAndMonth(staffId, month);
        if (!list.isEmpty()) return list;
        else return null;
    }

    @Override
    public TimesheetDTO checkDayStatus(List<Timesheet> list, int staffId, String month) {
        int daysOfMonth = dateUtil.getLengthOfMonth(month);
        TimesheetDTO dto = new TimesheetDTO();
        List<String> listStatus = new ArrayList<>();
        boolean check = false;
        if (list.size() < daysOfMonth) {
            for (int i = 1; i <= daysOfMonth; i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (Integer.parseInt(dateUtil.convertLocalDateToStringDay(list.get(j).getDate())) == i) {
                        listStatus.add(list.get(j).getDateStatus());
                        check = true;
                    }
                }
                if (check == false) {
                    listStatus.add(null);
                } else {
                    check = false;
                }
            }
        } else if (list.size() == daysOfMonth) {
            for (int i = 0; i < list.size(); i++) {
                listStatus.add(list.get(i).getDateStatus());
            }
        }
        List<Integer> listDayCheck = dateUtil.getListDayCheck(listStatus);
        dto.setStaffId(staffId);
        dto.setStaffName(staffRepository.findStaffById(staffId).getSurname() + " " + staffRepository.findStaffById(staffId).getFirstName());
        dto.setMonthYear(month);
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

    @Override
    public List<TimesheetDTO> getListTimeSheetByMonth(String monthYear) {
        List<TimesheetDTO> listDto = new ArrayList<>();
        List<Integer> listStaffId = staffRepository.getListStaffIdEnable();
        for (Integer staffId : listStaffId) {
            List<Timesheet> listTimesheetByMonth = getTimesheetByStaffIdAndMonth(staffId, monthYear);
            if(listTimesheetByMonth == null){
                listDto.add(new TimesheetDTO(staffId,
                        staffRepository.findStaffById(staffId).getSurname() + " " + staffRepository.findStaffById(staffId).getFirstName(),
                        monthYear,
                        null));
            }else {
                TimesheetDTO timesheetDTO = checkDayStatus(listTimesheetByMonth, staffId, monthYear);
                listDto.add(timesheetDTO);
            }
        }
        return listDto;
    }
}
