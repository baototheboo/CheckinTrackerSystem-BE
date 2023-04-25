package com.example.ctsbe.service;

import com.example.ctsbe.dto.complaint.ComplaintAddDTO;
import com.example.ctsbe.entity.Complaint;
import com.example.ctsbe.repository.ComplaintRepository;
import com.example.ctsbe.repository.ComplaintTypeRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Service
public class ComplaintServiceImpl implements ComplaintService{
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    DateUtil dateUtil = new DateUtil();
    @Override
    public Complaint addComplaint(ComplaintAddDTO dto) {
        Complaint complaint = new Complaint();
        complaint.setStaff(staffRepository.getById(getIdFromToken()));
        complaint.setCreatedDate(Instant.now());
        complaint.setContent(dto.getContent());
        complaint.setStatus("Pending");
        complaint.setComplaintType(complaintTypeRepository.getById(dto.getComplaintTypeId()));
        return complaintRepository.save(complaint);
    }

    @Override
    public Page<Complaint> getListComplaint(Pageable pageable) {
        return complaintRepository.getAllComplaint(pageable);
    }

    @Override
    public void updateComplaint(int id,int status) {
        Complaint complaint = complaintRepository.findById(id).get();
        complaint.setApprover(staffRepository.getById(getIdFromToken()));
        if(status == 1) complaint.setStatus("Accept");
        else if (status == 0) complaint.setStatus("Reject");
        complaint.setLastUpdated(Instant.now());
        complaintRepository.save(complaint);
    }

    @Override
    public Page<Complaint> getListComplaintByStatus(String status,Pageable pageable) {
        return complaintRepository.getListComplaintByStatus(status,pageable);
    }

    @Override
    public Page<Complaint> getListComplaintById(int id, Pageable pageable) {
        return complaintRepository.getListByStaffId(id, pageable);
    }

    @Override
    public Page<Complaint> getListComplaintByIdAndStatus(int id, String status, Pageable pageable) {
        return complaintRepository.getListByIdAndStatus(id, status, pageable);
    }

    public int getIdFromToken() {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        int id = jwtTokenUtil.getIdFromToken(jwtToken);
        return id;
    }
}
