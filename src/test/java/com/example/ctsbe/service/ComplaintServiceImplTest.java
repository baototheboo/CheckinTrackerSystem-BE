package com.example.ctsbe.service;

import com.example.ctsbe.dto.complaint.ComplaintAddDTO;
import com.example.ctsbe.entity.Complaint;
import com.example.ctsbe.repository.ComplaintRepository;
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
class ComplaintServiceImplTest {
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private ComplaintService complaintService;
    Pageable pageable = PageRequest.of(0, 50);

    /*@Test
    void addComplaint() {
        ComplaintAddDTO dto = new ComplaintAddDTO();
        dto.setContent("Tôi xin phép ngày mai đi muộn");
        dto.setComplaintTypeId(1);
        Complaint actualRes =complaintService.addComplaint(dto);
        Complaint expectedRes = complaintRepository.getComplaintByContentContain("Tôi xin phép ngày mai đi muộn");
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getListComplaint() {
        Page<Complaint> complaintPage = complaintService.getListComplaint(pageable);
        List<Complaint> actualRes = complaintPage.getContent();
        assertEquals(10,actualRes.size());
    }

    @Test
    void updateComplaint() {
    }

    @Test
    void getListComplaintByStatus() {
    }

    @Test
    void getListComplaintById() {
    }

    @Test
    void getListComplaintByIdAndStatus() {
    }*/
}