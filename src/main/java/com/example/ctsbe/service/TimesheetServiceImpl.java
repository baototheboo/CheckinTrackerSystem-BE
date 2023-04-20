package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.dto.timesheet.TimesheetResponseDTO;
import com.example.ctsbe.dto.timesheet.TimesheetUpdateDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.exception.ConnectionErrorException;
import com.example.ctsbe.exception.StaffNotAvailableException;
import com.example.ctsbe.exception.TimesheetNotExist;
import com.example.ctsbe.mapper.TimesheetMapper;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.repository.TimesheetRepository;
import com.example.ctsbe.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        if (list == null || list.size() < daysOfMonth) {
            for (int i = 1; i <= daysOfMonth; i++) {
                if (list == null && dateUtil.compareYearMonth(month, YearMonth.now())) {
                    listStatus.add(null);
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (Integer.parseInt(dateUtil.convertLocalDateToStringDay(list.get(j).getDate())) == i) {
                            listStatus.add(list.get(j).getDateStatus() + "-" + list.get(j).getNote());
                            check = true;
                        }
                    }
                    if (check == false) {
                        listStatus.add(null);
                    } else {
                        check = false;
                    }
                }
            }
        } else if (list.size() == daysOfMonth) {
            for (int i = 0; i < list.size(); i++) {
                listStatus.add(list.get(i).getDateStatus() + "-" + list.get(i).getNote());
            }
        }
        List<Integer> listDayCheck = dateUtil.getListDayCheck(listStatus);
        dto.setStaffId(staffId);
        dto.setStaffName(staffRepository.findStaffById(staffId).getFullName());
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
    public TimesheetResponseDTO getTimesheetByStaffAndDate(int staffId, LocalDate date) {
        Optional<Staff> staff = staffRepository.findAvailableStaffById(staffId);
        if (staff.isEmpty()) {
            throw new StaffNotAvailableException("Staff không khả dụng hoặc không tồn tại.");
        }
        TimesheetResponseDTO timesheetResponseDTO = timesheetRepository.getByStaffAndDate(staffId, date);
        return timesheetResponseDTO;
    }

    @Override
    public void updateTimesheetStatus(int hrId, int staffId, TimesheetUpdateDTO timesheetUpdateDTO) {
        Staff hr = staffRepository.findAvailableHRById(hrId);
        if (hr == null) {
            throw new StaffNotAvailableException("HR không khả dụng hoặc không tồn tại.");
        }
        Optional<Staff> staff = staffRepository.findAvailableStaffById(staffId);
        if (staff.isEmpty()) {
            throw new StaffNotAvailableException("Staff không khả dụng hoặc không tồn tại.");
        }
        Timesheet timesheet = timesheetRepository.findByStaffAndAndDate(staff, timesheetUpdateDTO.getDate());
        if (timesheet == null) {
            throw new TimesheetNotExist("Không tìm thấy thông tin của ngày.");
        } else {
            timesheet.setDateStatus(timesheetUpdateDTO.getDateStatus());
//            timesheet.setNote((timesheetUpdateDTO.getNote() == null || Objects.equals(timesheetUpdateDTO.getNote(), ""))
//                    ? null : timesheetUpdateDTO.getNote());
            timesheet.setDayWorkingStatus(timesheetUpdateDTO.getDayWorkingStatus());
            timesheet.setUpdatedHistory("Được thay đổi lần cuối bởi " + hr.getFullName());
            timesheet.setLastUpdated(Instant.now());
            timesheetRepository.save(timesheet);
        }
    }

    @Override
    public List<TimesheetDTO> getListTimeSheetByMonth(String monthYear) {
        List<TimesheetDTO> listDto = new ArrayList<>();
        List<Integer> listStaffId = staffRepository.getListStaffIdEnable();
        for (Integer staffId : listStaffId) {
            List<Timesheet> listTimesheetByMonth = getTimesheetByStaffIdAndMonth(staffId, monthYear);
            Staff staff = staffRepository.findStaffById(staffId);
            if (listTimesheetByMonth == null) {
                listDto.add(new TimesheetDTO(staffId,
                        staff.getFullName(),
                        monthYear,
                        null));
            } else {
                TimesheetDTO timesheetDTO = checkDayStatus(listTimesheetByMonth, staffId, monthYear);
                listDto.add(timesheetDTO);
            }
        }
        return listDto;
    }
}
