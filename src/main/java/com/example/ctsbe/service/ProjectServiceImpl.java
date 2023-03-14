package com.example.ctsbe.service;

import com.example.ctsbe.entity.Project;
import com.example.ctsbe.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public Page<Project> getAllProject(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> getProjectByNameContain(String name, Pageable pageable) {
        return projectRepository.findByProjectNameContaining(name, pageable);
    }

    @Override
    public Project getProjectById(int id) {
        return projectRepository.getById(id);
    }

}
