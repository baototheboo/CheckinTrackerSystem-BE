package com.example.ctsbe.tasks;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.repository.ImageVerifyRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.repository.TimesheetRepository;
import com.example.ctsbe.service.HolidayService;
import com.example.ctsbe.service.ImageVerifyService;
import com.example.ctsbe.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class ScheduledCheckIn {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private HolidayService holidayService;

    private static int minus_day = 1;

    @PostConstruct
    public void init() {
        checkInForgot(); // Thực hiện job ngay khi ứng dụng được khởi động
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void checkInForgot() {
//        for (int i = 42; i <= 102 ; i++){
//            int minus_day = i;
        Instant startTime = DateUtil.convertLocalDateTimeToInstant(LocalDate.now().atStartOfDay().minusDays(minus_day));
        Instant endTime = DateUtil.convertLocalDateTimeToInstant(LocalDate.now().atStartOfDay().minusDays(minus_day).minusSeconds(1));
        List<Staff> staffAbsent = staffRepository.findStaffAbsent(startTime, endTime);
        if(CollectionUtils.isEmpty(staffAbsent)) {
            return;
        } else {
            try {
                for (Staff staff : staffAbsent) {
                    List<Timesheet> check = timesheetRepository.getTimesheetByStaffAndAndDate(staff, startTime.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalDate());
                    if (check.isEmpty()){
                        LocalDate date = LocalDate.now().minusDays(minus_day);
                        Timesheet timesheet = new Timesheet();
                        timesheet.setStaff(staff);
                        timesheet.setDate(date);
                        timesheet.setDateStatus("ABSENT");
                        if (holidayService.checkHoliday(date)){
                            timesheet.setNote("Ngày nghỉ lễ");
                            timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_DEFAULT);
                        } else if (DateUtil.checkWeekend(date)) {
                            timesheet.setNote("Cuối tuần");
                            timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_ABSENT);
                        }
                        else {
                            timesheet.setNote("Vắng");
                            timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_ABSENT);
                        }
                        timesheet.setLastUpdated(Instant.now());
                        timesheetRepository.save(timesheet);
                    }
                }
             }catch (Exception e){
                logger.error("Không thể đánh vắng cho nhân viên");
            }
        }
        //Check working hour = 0, không check out => vắng
    }
//    }
}
