package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.repository.TimesheetRepository;
import com.example.ctsbe.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TimesheetServiceImplTest {
    @Autowired
    private TimesheetService timesheetService;
    @Autowired
    private TimesheetRepository timesheetRepository;
    DateUtil dateUtil = new DateUtil();

   /* @Test
    void getTimesheetByStaffIdAndMonth() {
        List<Timesheet> list = timesheetService.getTimesheetByStaffIdAndMonth(8,"2023-03");
        assertEquals(31,list.size());
    }

    @Test
    void checkDayStatus() {
        List<Timesheet> list = timesheetService.getTimesheetByStaffIdAndMonth(8, "2023-03");
        TimesheetDTO actualRes = timesheetService.checkDayStatus(list,8,"2023-03");
        TimesheetDTO expectedRes = new TimesheetDTO();
        expectedRes.setStaffId(8);
        expectedRes.setStaffName("Vu Duc Bao");
        expectedRes.setMonthYear("2023-03");
        Integer[] a = {3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 5, 5, 3, 2, 1, 1, 2};
        List<Integer> listCheck = Arrays.asList(a);
        expectedRes.setDayCheck(listCheck);
        assertEquals(expectedRes,actualRes);
    }

    //@Test
    //void getListDayOfLocalDate() {
    //}

    @Test
    void getTimesheetByStaffAndDate() {
    }

    @Test
    void updateTimesheetStatus() {
    }

    @Test
    void getListTimeSheetByMonth() {
        List<TimesheetDTO> dtoList = timesheetService.getListTimeSheetByMonth("2023-03");
        TimesheetDTO actualRes = dtoList.get(0);
        TimesheetDTO expectedRes = new TimesheetDTO();
        expectedRes.setStaffId(1);
        expectedRes.setStaffName("Le Manh Hung");
        expectedRes.setMonthYear("2023-03");
        Integer[] a = {3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 5, 5, 3, 2, 2, 1, 3};
        List<Integer> listCheck = Arrays.asList(a);
        expectedRes.setDayCheck(listCheck);
        assertEquals(expectedRes,actualRes);
    }*/
}