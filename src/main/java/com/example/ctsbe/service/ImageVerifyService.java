package com.example.ctsbe.service;

import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.dto.staff.RecognizedStaffDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ImageVerifyService {
    void saveImageForSetup(List<String> images, Staff staff);
    ImagesVerify saveImageForVerify(ImageSetupVggDTO imageSetupVggDTO,
                                    LocalDateTime localDateTime, RecognizedStaffDTO recognizedStaffDTO);
    ImagesVerify saveImageToFolder(List<String> images, String relativePath,
                                  LocalDateTime localDateTime, String fullName,
                                  Float probability, Integer staffId,
                                  Boolean setupEmployeeImage);
    Page<ImageVerifyDTO> findImageVerify(Integer staffId, String  name, boolean onlyMe,LocalDate startDate, LocalDate endDate, boolean isError, Pageable pageable);

}
