package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.dto.timesheet.TimesheetResponseDTO;
import com.example.ctsbe.dto.timesheet.TimesheetUpdateDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.exception.ConnectionErrorException;
import com.example.ctsbe.exception.StaffNotAvailableException;
import com.example.ctsbe.exception.TimesheetNotExist;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.repository.TimesheetRepository;
import com.example.ctsbe.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return timesheetRepository.getListTimesheetByStaffIdAndMonth(staffId, month);
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
                if(check == false){
                    listStatus.add(null);
                }else {
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
    public void updateTimesheetStatus(int staffId, TimesheetUpdateDTO timesheetUpdateDTO) {
            Optional<Staff> staff = staffRepository.findAvailableStaffById(staffId);
            if (staff.isEmpty()) {
                throw new StaffNotAvailableException("Staff không khả dụng hoặc không tồn tại.");
            }
            Timesheet timesheet = timesheetRepository.findByStaffAndAndDate(staff, timesheetUpdateDTO.getDate());
            if (timesheet == null) {
                throw new TimesheetNotExist("Không tìm thấy thông tin của ngày.");
            }else {
                timesheet.setDateStatus(timesheetUpdateDTO.getDateStatus());
                timesheet.setNote(timesheetUpdateDTO.getNote());
                timesheetRepository.save(timesheet);
            }
    }
}
