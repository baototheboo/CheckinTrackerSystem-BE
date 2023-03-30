package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet,Integer> {

    @Query(value = "SELECT ts FROM Timesheet ts WHERE ts.staff.id = :staffId AND ts.date = :date AND ts.dateStatus IN (:dateStatusOk, :dateStatusLate)")
    List<Timesheet> findCheckedInStaff(Integer staffId, LocalDate date, String dateStatusOk, String dateStatusLate);

    @Query(value = "select ts from Timesheet ts where ts.staff.id =:staffId" +
            " and function('date_format',ts.date,'%Y-%m') =:yearMonth order by ts.date asc")
    List<Timesheet> getListTimesheetByStaffIdAndMonth(int staffId,String yearMonth);



}
