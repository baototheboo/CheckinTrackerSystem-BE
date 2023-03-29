package com.example.ctsbe.service;

import com.example.ctsbe.dto.complaint.ComplaintAddDTO;
import com.example.ctsbe.entity.Complaint;
import com.example.ctsbe.repository.ComplaintRepository;
import com.example.ctsbe.repository.ComplaintTypeRepository;
import com.example.ctsbe.repository.StaffRepository;
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
    @Override
    public void addComplaint(ComplaintAddDTO dto) {
        Complaint complaint = new Complaint();
        complaint.setStaff(staffRepository.getById(getIdFromToken()));
        complaint.setCreatedDate(Instant.now());
        complaint.setLastUpdated(Instant.now());
        complaint.setContent(dto.getContent());
        complaint.setStatus("Pending");
        complaint.setComplaintType(complaintTypeRepository.getById(dto.getComplaintTypeId()));
        complaintRepository.save(complaint);
    }

    @Override
    public Page<Complaint> getListComplaint(Pageable pageable) {
        return complaintRepository.findAll(pageable);
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
        return complaintRepository.findByStatus(status,pageable);
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
