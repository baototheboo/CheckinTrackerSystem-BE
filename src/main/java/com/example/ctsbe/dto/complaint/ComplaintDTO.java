package com.example.ctsbe.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {
    private Integer id;
    private String staffName;
    private String createDay;
    private String content;
    private String status;
    private String complaintType;
}
