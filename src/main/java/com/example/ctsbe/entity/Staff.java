package com.example.ctsbe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    @Size(max = 255)
    @Column(name = "facial_recognition_status")
    private String facialRecognitionStatus;

    @NotNull
    @Column(name = "enable", nullable = false)
    private Byte enable;

    @Column(name = "last_trained_time")
    private Instant lastTrainedTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promotion_level_id", nullable = false)
    private PromotionLevel promotionLevel;

    @OneToMany(mappedBy = "staff")
    private Set<ImagesSetup> imagesSetups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<MonthlyReport> monthlyReports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<Timesheet> timesheets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<Account> accounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<StaffProject> staffProjects = new LinkedHashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<Complaint> complaints = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacialRecognitionStatus() {
        return facialRecognitionStatus;
    }

    public void setFacialRecognitionStatus(String facialRecognitionStatus) {
        this.facialRecognitionStatus = facialRecognitionStatus;
    }

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    public Instant getLastTrainedTime() {
        return lastTrainedTime;
    }

    public void setLastTrainedTime(Instant lastTrainedTime) {
        this.lastTrainedTime = lastTrainedTime;
    }

    public PromotionLevel getPromotionLevel() {
        return promotionLevel;
    }

    public void setPromotionLevel(PromotionLevel promotionLevel) {
        this.promotionLevel = promotionLevel;
    }

    public Set<ImagesSetup> getImagesSetups() {
        return imagesSetups;
    }

    public void setImagesSetups(Set<ImagesSetup> imagesSetups) {
        this.imagesSetups = imagesSetups;
    }

    public Set<MonthlyReport> getMonthlyReports() {
        return monthlyReports;
    }

    public void setMonthlyReports(Set<MonthlyReport> monthlyReports) {
        this.monthlyReports = monthlyReports;
    }

    public Set<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<StaffProject> getStaffProjects() {
        return staffProjects;
    }

    public void setStaffProjects(Set<StaffProject> staffProjects) {
        this.staffProjects = staffProjects;
    }

    public Set<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        this.complaints = complaints;
    }

}