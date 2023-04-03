package com.example.ctsbe.repository;

import com.example.ctsbe.entity.MonthlyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport,Integer> {
    @Query(value = "select mr from MonthlyReport mr" +
            " where mr.staff.id =:staffId " +
            "and function('date_format',mr.month,'%Y-%m') =:monthYear")
    Page<MonthlyReport> getListReportByIdAndMonthYear(int staffId, String monthYear, Pageable pageable);

    @Query(value = "select mr from MonthlyReport mr" +
            " where function('date_format',mr.month,'%Y-%m') =:monthYear")
    Page<MonthlyReport> getListReportByMonthYear(String monthYear, Pageable pageable);

    @Query(value = "select mr from MonthlyReport mr" +
            " where function('date_format',mr.month,'%Y-%m') =:monthYear")
    List<MonthlyReport> getReportByMonthYear(String monthYear);
}
