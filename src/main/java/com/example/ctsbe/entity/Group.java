package com.example.ctsbe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @NotNull
    @Column(name = "group_leader_id", nullable = false)
    private Integer groupLeaderId;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @OneToMany(mappedBy = "group")
    private Set<Project> projects = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(Integer groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}