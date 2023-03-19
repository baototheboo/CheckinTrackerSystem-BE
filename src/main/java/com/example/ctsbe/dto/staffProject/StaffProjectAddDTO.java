package com.example.ctsbe.dto.staffProject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffProjectAddDTO {
    private List<Integer> staffId;
    private Integer projectId;
}
