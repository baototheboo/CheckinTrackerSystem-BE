package com.example.ctsbe.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "staff_project")
public class StaffProject {
    @EmbeddedId
    private StaffProjectId id;

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @MapsId("staffId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Size(max = 45)
    @Column(name = "created_date", length = 45)
    private String createdDate;


    public StaffProjectId getId() {
        return id;
    }

    public void setId(StaffProjectId id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


}