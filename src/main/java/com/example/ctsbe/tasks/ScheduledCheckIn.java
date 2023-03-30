package com.example.ctsbe.tasks;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.repository.ImageVerifyRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.repository.TimesheetRepository;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Scheduled(cron = "0 0 0 * * *")
    public void checkInForgot() {
        Instant startTime = DateUtil.convertLocalDateTimeToInstant(LocalDate.now().atStartOfDay().minusDays(1));
        Instant endTime = DateUtil.convertLocalDateTimeToInstant(LocalDate.now().atStartOfDay().minusSeconds(1));
        List<Staff> staffAbsent = staffRepository.findStaffAbsent(startTime, endTime);
        if(CollectionUtils.isEmpty(staffAbsent)) {
            return;
        } else {
            try {
                for (Staff staff : staffAbsent) {
                    Timesheet timesheet = new Timesheet();
                    timesheet.setStaff(staff);
                    timesheet.setDate(LocalDate.now().minusDays(1));
                    timesheet.setDateStatus("ABSENT");
                    timesheet.setNote("Vắng");
                    timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_ABSENT);
                    timesheetRepository.save(timesheet);
                    logger.info("%",staff.getId());
                }
             }catch (Exception e){
                logger.error("Không thể đánh vắng cho nhân viên");
            }
        }
    }
}
