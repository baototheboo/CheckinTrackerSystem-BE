package com.example.ctsbe.service;

import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.repository.StaffRepository;
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
class StaffServiceImplTest {
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;
    Pageable pageable = PageRequest.of(0, 50);

    //@Test
    //void addStaff() {
    //}

    /*@Test
    void getAllStaff() {
        Page<Staff> staffPage = staffService.getAllStaff(pageable);
        List<Staff> actualRes = staffPage.getContent();
        assertEquals(41,actualRes.size());
    }

    @Test
    void getStaffByName() {
        Page<Staff> staffPage = staffService.getStaffByName("hu","hu",pageable);
        List<Staff> actualRes = staffPage.getContent();
        assertEquals(8,actualRes.size());
    }

    @Test
    void findStaffByEmail() {
        Staff staff = staffService.findStaffByEmail("huy@gmail.com");
        StaffDTO actualRes = StaffMapper.convertStaffToStaffDto(staff);
        StaffDTO expectedRes = new StaffDTO(14,"huy@gmail.com","Tran Gia Huy"
                ,"1996-10-24","0123456789",1,"PROJECT MANAGER");
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void changePromotionLevel() {
        int expectedRes = 3;
        staffService.changePromotionLevel(14,expectedRes);
        int actualRes = staffRepository.findStaffById(14).getPromotionLevel().getId();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListAvailableStaff() {
        List<Staff> list = staffService.getListAvailableStaff(2);
        int expectedRes = 2;
        int actualRes = list.size();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getStaffsByRole() {
        List<Staff> list = staffService.getStaffsByRole(3);
        int expectedRes = 7;
        int actualRes = list.size();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListStaffAddToGroup() {
        List<Staff> list = staffService.getListStaffAddToGroup();
        int expectedRes = 0;
        int actualRes = list.size();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListPMAvailable() {
        List<Staff> list = staffService.getListPMAvailable();
        int expectedRes = 1;
        int actualRes = list.size();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListGLAvailable() {
        List<Staff> list = staffService.getListGLAvailable();
        int expectedRes = 0;
        int actualRes = list.size();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListStaffByGroup() {
        Page<Staff> staffPage = staffService.getListStaffByGroup(3,pageable);
        List<Staff> actualRes = staffPage.getContent();
        assertEquals(6,actualRes.size());
    }*/
}