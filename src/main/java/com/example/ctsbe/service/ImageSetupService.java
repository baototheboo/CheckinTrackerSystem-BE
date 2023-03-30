package com.example.ctsbe.service;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageSetupService {

    void saveImageForSetup(List<String> images, Staff staff);

    Page<ImageSetupDTO> findImageSetup(Integer staffId, Pageable pageable);
}
