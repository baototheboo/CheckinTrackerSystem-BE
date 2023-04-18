package com.example.ctsbe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "timesheet")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_check_in")
    private Instant timeCheckIn;

    @Column(name = "time_check_out")
    private Instant timeCheckOut;

    @Size(max = 255)
    @NotNull
    @Column(name = "date_status", nullable = false)
    private String dateStatus;

    @Size(max = 45)
    @Column(name = "day_working_status", length = 45)
    private String dayWorkingStatus;
    @Size(max = 1000)
    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "workingHours")
    private Double workingHours;

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getTimeCheckIn() {
        return timeCheckIn;
    }

    public void setTimeCheckIn(Instant timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public Instant getTimeCheckOut() {
        return timeCheckOut;
    }

    public void setTimeCheckOut(Instant timeCheckOut) {
        this.timeCheckOut = timeCheckOut;
    }

    public String getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(String dateStatus) {
        this.dateStatus = dateStatus;
    }


}