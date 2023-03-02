package com.example.ctsbe.service;

import com.example.ctsbe.dto.StaffDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StaffServiceImpl implements StaffService{
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PromotionLevelService promotionLevelService;

    @Override
    public Staff addStaff(StaffDTO staffDTO) {
        Staff staff = convertStaffDTOToStaff(staffDTO);
         return staffRepository.save(staff);
    }

    public Staff convertStaffDTOToStaff(StaffDTO dto){
        DateUtil dateUtil = new DateUtil();
        Staff  staff = new Staff();
        staff.setEmail(dto.getEmail());
        staff.setFirstName(dto.getFirstName());
        staff.setSurname(dto.getSurname());
        staff.setCreatedDate(Instant.now());
        staff.setLastUpdated(Instant.now());
        staff.setDateOfBirth(dateUtil.convertStringToInstant(dto.getDateOfBirth() + " 00:00:00"));
        staff.setPhone(dto.getPhone());
        staff.setEnable((byte)1);
        staff.setPromotionLevel(promotionLevelService.getPromotionLevelById(dto.getPromotionLevelId()));
        return  staff;
    }
}
