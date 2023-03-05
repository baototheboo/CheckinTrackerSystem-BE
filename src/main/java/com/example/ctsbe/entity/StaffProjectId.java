package com.example.ctsbe.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StaffProjectId implements Serializable {
    private static final long serialVersionUID = 5955254630495481327L;
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @NotNull
    @Column(name = "staff_id", nullable = false)
    private Integer staffId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StaffProjectId entity = (StaffProjectId) o;
        return Objects.equals(this.projectId, entity.projectId) &&
                Objects.equals(this.staffId, entity.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, staffId);
    }

}