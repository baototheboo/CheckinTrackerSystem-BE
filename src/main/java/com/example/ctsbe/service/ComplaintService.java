package com.example.ctsbe.service;

import com.example.ctsbe.dto.complaint.ComplaintAddDTO;
import com.example.ctsbe.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComplaintService {
    void addComplaint(ComplaintAddDTO dto);

    Page<Complaint> getListComplaint(Pageable pageable);

    void updateComplaint(int id,int status);

    Page<Complaint> getListComplaintByStatus(String status,Pageable pageable);

}
