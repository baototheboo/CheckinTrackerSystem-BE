package com.example.ctsbe.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Data
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

    @Column(name = "late_check_in_minutes")
    private Double lateCheckInMinutes;

    @Column(name = "early_check_out_minutes")
    private Double earlyCheckOutMinutes;

    @Column(name = "working_hours")
    private Double workingHours;

    @Size(max = 1000)
    @Column(name = "updated_history", length = 1000)
    private String updatedHistory;

    @Column(name = "last_updated")
    private Instant lastUpdated;


}