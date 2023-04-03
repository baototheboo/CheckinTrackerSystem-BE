package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.complaintType.ComplaintTypeDTO;
import com.example.ctsbe.entity.ComplaintType;

public class ComplaintTypeMapper {
    public static ComplaintTypeDTO convertEntityToDTO(ComplaintType complaintType){
        ComplaintTypeDTO dto = new ComplaintTypeDTO();
        dto.setId(complaintType.getId());
        dto.setName(complaintType.getName());
        return dto;
    }
}
