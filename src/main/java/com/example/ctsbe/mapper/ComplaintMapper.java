package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.complaint.ComplaintDTO;
import com.example.ctsbe.entity.Complaint;
import com.example.ctsbe.util.DateUtil;

public class ComplaintMapper {
    public static ComplaintDTO convertEntityToDto(Complaint complaint) {
        DateUtil dateUtil = new DateUtil();
        ComplaintDTO dto = new ComplaintDTO();
        dto.setId(complaint.getId());
        dto.setStaffName(complaint.getStaff().getSurname() + " " + complaint.getStaff().getFirstName());
        dto.setCreateDay(dateUtil.convertInstantToStringYearMonthDay(complaint.getCreatedDate()));
        dto.setContent(complaint.getContent());
        dto.setStatus(complaint.getStatus());
        dto.setComplaintType(complaint.getComplaintType().getName());
        return dto;
    }
}
