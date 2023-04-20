package com.example.ctsbe.repository;

import com.example.ctsbe.dto.timesheet.TimesheetResponseDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet,Integer> {

    @Query(value = "SELECT new com.example.ctsbe.dto.timesheet.TimesheetResponseDTO(t.id, t.staff.id, t.staff.firstName," +
            "t.staff.surname, t.date, t.timeCheckIn, t.timeCheckOut, t.dateStatus, t.note, t.workingHours," +
            "t.dayWorkingStatus, t.lateCheckInMinutes, t.earlyCheckOutMinutes, t.updatedHistory, t.lastUpdated" +
            ") FROM Timesheet as t where t.staff.id = :staffId and t.date = :date")
    TimesheetResponseDTO getByStaffAndDate(@Param("staffId") int staffId,@Param("date") LocalDate date);


    @Query(value = "SELECT ts FROM Timesheet ts WHERE ts.staff.id = :staffId AND ts.date = :date AND ts.dateStatus IN (:dateStatusOk, :dateStatusLate)")
    Timesheet findCheckedInStaff(Integer staffId, LocalDate date, String dateStatusOk, String dateStatusLate);

    @Query(value = "select ts from Timesheet ts where ts.staff.id =:staffId" +
            " and function('date_format',ts.date,'%Y-%m') =:yearMonth order by ts.date asc")
    List<Timesheet> getListTimesheetByStaffIdAndMonth(int staffId,String yearMonth);

    List<Timesheet> getTimesheetByStaffAndAndDate(Staff staff, LocalDate date);

    Timesheet findByStaffAndAndDate(Optional<Staff> staff, LocalDate date);
}
