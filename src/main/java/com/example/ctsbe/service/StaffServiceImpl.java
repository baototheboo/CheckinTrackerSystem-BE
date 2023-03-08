package com.example.ctsbe.service;

import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.PromotionLevel;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StaffServiceImpl implements StaffService{
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PromotionLevelService promotionLevelService;

    @Override
    public Staff addStaff(StaffAddDTO staffAddDTO) {
        Staff staff = convertStaffAddDTOToStaff(staffAddDTO);
         return staffRepository.save(staff);
    }

    @Override
    public Page<Staff> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }

    @Override
    public Page<Staff> getStaffByName(String surname,String firstname, Pageable pageable) {
        return staffRepository.findBySurnameContainingOrFirstNameContaining(surname,firstname,pageable);
    }

    @Override
    public void changeEnableStaff(int id) {
        Staff staff = staffRepository.getById(id);
        if(staff.getEnable() == 1){
            staff.setEnable((byte)0);
        } else {
            staff.setEnable((byte)1);
        }
        staffRepository.save(staff);
    }

    @Override
    public void changePromotionLevel(int staffId,int levelId) {
        Staff staff = staffRepository.getById(staffId);
        staff.setPromotionLevel(promotionLevelService.getPromotionLevelById(levelId));
        staffRepository.save(staff);
    }


    public Staff convertStaffAddDTOToStaff(StaffAddDTO dto){
        DateUtil dateUtil = new DateUtil();
        Staff  staff = new Staff();
        staff.setEmail(dto.getEmail());
        staff.setFirstName(dto.getFirstName());
        staff.setSurname(dto.getSurname());
        staff.setCreatedDate(Instant.now());
        staff.setLastUpdated(Instant.now());
        staff.setDateOfBirth(dateUtil.convertStringToLocalDate(dto.getDateOfBirth()));
        staff.setPhone(dto.getPhone());
        staff.setEnable((byte)1);
        staff.setPromotionLevel(promotionLevelService.getPromotionLevelById(dto.getPromotionLevelId()));
        return  staff;
    }
}
