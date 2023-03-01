package com.example.ctsbe.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "monthly_report")
public class MonthlyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "month")
    private Instant month;

    @Column(name = "active_day")
    private Integer activeDay;

    @Column(name = "late_day")
    private Integer lateDay;

    @Column(name = "off_day")
    private Integer offDay;

    @Column(name = "working_hour")
    private Integer workingHour;

    @Column(name = "created_date")
    private Instant createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Instant getMonth() {
        return month;
    }

    public void setMonth(Instant month) {
        this.month = month;
    }

    public Integer getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(Integer activeDay) {
        this.activeDay = activeDay;
    }

    public Integer getLateDay() {
        return lateDay;
    }

    public void setLateDay(Integer lateDay) {
        this.lateDay = lateDay;
    }

    public Integer getOffDay() {
        return offDay;
    }

    public void setOffDay(Integer offDay) {
        this.offDay = offDay;
    }

    public Integer getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(Integer workingHour) {
        this.workingHour = workingHour;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}