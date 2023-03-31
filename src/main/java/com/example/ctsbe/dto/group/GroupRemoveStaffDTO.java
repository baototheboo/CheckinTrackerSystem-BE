package com.example.ctsbe.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRemoveStaffDTO {
    private List<Integer> staffId;
}
