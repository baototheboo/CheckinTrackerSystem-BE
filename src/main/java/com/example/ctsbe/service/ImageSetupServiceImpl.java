package com.example.ctsbe.service;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.exception.ImageNotFoundException;
import com.example.ctsbe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.util.List;

@Service
public class ImageSetupServiceImpl implements ImageSetupService{

    @Autowired
    ImageVerifyService imageVerifyService;

    @Autowired
    ImageSetupRepository imageSetupRepository;


    private static String errorPath = "/error-by-date/";

    private static String successPath = "/success-by-date/";

    private static String setupPath = "/setup/";

    @Override
    public void saveImageForSetup(List<String> images, Staff staff) {
        String fullName = staff != null ? staff.getFullName().trim().replace(" ", "_") : "";
        LocalDateTime timeSetup = LocalDateTime.now();
        if (!CollectionUtils.isEmpty(images)) {
            imageVerifyService.saveImageToFolder(images, setupPath, timeSetup, fullName, null, staff.getId(), true);
        }
    }

    @Override
    public Page<ImageSetupDTO> findImageSetup(Integer staffId, Pageable pageable) {
        Page<ImageSetupDTO> imageSetupDTOS = imageSetupRepository.findImageSetupByStaffId(staffId, pageable);
        if (imageSetupDTOS.isEmpty()){
            throw new ImageNotFoundException("Không tìm thấy ảnh đã được setup nào");
        }
        else return imageSetupDTOS;
    }


}
