package com.example.ctsbe.service;

import com.example.ctsbe.dto.project.ProjectAddDTO;
import com.example.ctsbe.dto.project.ProjectDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.mapper.ProjectMapper;
import com.example.ctsbe.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectServiceImplTest {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    Pageable pageable = PageRequest.of(0, 50);

    /*@Test
    void getAllProject() {
        Page<Project> projectPage = projectService.getAllProject(pageable);
        List<Project> actualRes = projectPage.getContent();
        assertEquals(16,actualRes.size());
    }

    @Test
    void addProject() {
        ProjectAddDTO dto = new ProjectAddDTO();
        dto.setProjectName("Test new project");
        dto.setGroupId(3);
        dto.setProjectManagerId(23);
        Project actualRes = projectService.addProject(dto);
        Project expectedRes = projectRepository.findProjectByProjectName("Test new project");
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListProjectByPMId() {
        Page<Project> projectPage = projectService.getListProjectByPMId(3,pageable);
        List<Project> actualRes = projectPage.getContent();
        assertEquals(3,actualRes.size());
    }

    @Test
    void getListProjectByPMIdAndProjectName() {
        Page<Project> projectPage = projectService.getListProjectByPMIdAndProjectName(3,"ank",pageable);
        List<Project> actualRes = projectPage.getContent();
        assertEquals(2,actualRes.size());
    }

    @Test
    void getProjectByNameContain() {
        Page<Project> projectPage = projectService.getProjectByNameContain("oo",pageable);
        List<Project> actualRes = projectPage.getContent();
        assertEquals(2,actualRes.size());
    }

    @Test
    void getProjectById() {
        Project project = projectService.getProjectById(5);
        ProjectDTO actualRes = ProjectMapper.convertEntityToDto(project);
        ProjectDTO expectedRes = new ProjectDTO(5,"mSale Project","Do Xuan Quang",9,"Java Group",6,"Done");
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void editProject() {
        ProjectAddDTO dto = new ProjectAddDTO();
        dto.setProjectName("mSale Pro");
        dto.setGroupId(6);
        dto.setProjectManagerId(9);
        projectService.editProject(5,dto);
        Project getById = projectService.getProjectById(5);
        Project getByName = projectRepository.findProjectByProjectName("mSale Pro");
        assertEquals(getById,getByName);
    }

    @Test
    void changeProjectStatus() {
        projectService.changeProjectStatus(6,2);
        String expectedRes = "Done";
        String actualRes = projectService.getProjectById(6).getStatus();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListProjectByGroupId() {
        List<Project> list = projectService.getListProjectByGroupId(2);
        assertEquals(5,list.size());
    }*/
}