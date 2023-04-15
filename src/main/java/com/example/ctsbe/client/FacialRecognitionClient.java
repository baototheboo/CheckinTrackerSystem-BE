package com.example.ctsbe.client;


import com.example.ctsbe.dto.staff.RecognizedStaffDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.dto.staff.StaffVerifyDTO;
import com.example.ctsbe.entity.Staff;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FacialRecognitionClient {
    String setupStaffForFacialRecognition(Staff staff, List<String> imgs);

    StaffVerifyDTO verifyStaffByFacialRecognition(LocalDateTime currentDateTime, ImageSetupVggDTO imageSetupVggDTO);

    RecognizedStaffDTO getRecognisedEmployeeDTOFromFacialRecognition(ImageSetupVggDTO imageSetupVggDTO);

    void deleteStaffSetup(int staffId);
}
