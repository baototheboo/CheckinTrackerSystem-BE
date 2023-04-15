package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.complaint.ComplaintDTO;
import com.example.ctsbe.entity.Complaint;
import com.example.ctsbe.util.DateUtil;

public class ComplaintMapper {
    public static ComplaintDTO convertEntityToDto(Complaint complaint) {
        DateUtil dateUtil = new DateUtil();
        ComplaintDTO dto = new ComplaintDTO();
        dto.setId(complaint.getId());
        dto.setStaffName(complaint.getStaff().getFullName());
        dto.setStaffId(complaint.getStaff().getId());
        dto.setCreateDay(dateUtil.convertInstantToStringYearMonthDay(complaint.getCreatedDate()));
        dto.setContent(complaint.getContent());
        dto.setStatus(complaint.getStatus());
        dto.setApproveName((complaint.getApprover() == null) ? null :
                complaint.getApprover().getFullName());
        dto.setApproveId((complaint.getApprover() == null) ? null : complaint.getApprover().getId());
        dto.setComplaintType(complaint.getComplaintType().getName());
        dto.setLastUpdate((complaint.getLastUpdated() == null) ? null :
                dateUtil.convertInstantToStringYearMonthDay(complaint.getLastUpdated()));
        return dto;
    }
}
