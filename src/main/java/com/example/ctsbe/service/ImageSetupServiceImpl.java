package com.example.ctsbe.service;

import com.example.ctsbe.client.FacialRecognitionClient;
import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.entity.ImagesSetup;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.exception.ImageNotFoundException;
import com.example.ctsbe.exception.StaffDoesNotExistException;
import com.example.ctsbe.repository.*;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.*;
import java.util.List;
import java.util.UUID;

@Service
public class ImageSetupServiceImpl implements ImageSetupService{

    @Autowired
    ImageVerifyService imageVerifyService;

    @Autowired
    ImageSetupRepository imageSetupRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    FacialRecognitionClient facialRecognitionClient;

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
        return imageSetupDTOS;
    }

    @Transactional
    @Override
    public void removeStaffSetup(int staffId){
        Staff staff = staffRepository.findStaffById(staffId);
        if (staff == null) {
            throw new StaffDoesNotExistException(staffId);
        }
        facialRecognitionClient.deleteStaffSetup(staffId);
        staff.setFacialRecognitionStatus(null);

        List<ImagesSetup> imagesSetupList = imageSetupRepository.findImageSetupByStaffId(staff.getId());
        for (ImagesSetup imagesSetup: imagesSetupList) {
            imagesSetup.setLastUpdated(DateUtil.convertLocalDateTimeToInstant(LocalDateTime.now()));
            imagesSetup.setStatus("REMOVED");
        }
        imageSetupRepository.saveAll(imagesSetupList);
        staffRepository.save(staff);
    }
}
