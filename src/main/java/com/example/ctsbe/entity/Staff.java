package com.example.ctsbe.entity;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
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
    private LocalDate dateOfBirth;

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    @Size(max = 255)
    @Column(name = "facial_recognition_status")
    private String facialRecognitionStatus;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "projectManager")
    private Set<Project> projects = new LinkedHashSet<>();

    @OneToMany(mappedBy = "groupLeader")
    private Set<Group> groups = new LinkedHashSet<>();

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public String getStaffName() {
        String name = this.firstName;
        if (!StringUtils.isEmpty(this.surname)) {
            String[] lastNames = this.surname.split(" ");
            for (int i = 0; i< lastNames.length; i ++) {
                name += "_" + lastNames[i];
            }
        }
        return name;
    }
    public String getFullName() {
        return String.format("%s %s", getFirstName(), getSurname());
    }
}