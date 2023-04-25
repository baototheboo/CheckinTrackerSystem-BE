package com.example.ctsbe.controller;


import com.example.ctsbe.client.FacialRecognitionClient;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.dto.vgg.ImageVerifyVggDTO;
import com.example.ctsbe.dto.staff.StaffVerifyDTO;
import com.example.ctsbe.entity.ImagesSetup;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.enums.FacialRecognitionStatus;
import com.example.ctsbe.exception.SetupEmployeeFacialRecognitionException;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.service.ImageSetupService;
import com.example.ctsbe.service.ImageVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/check-in")
@CrossOrigin(origins = "*")
public class CheckInController {

    private static final Logger logger = LoggerFactory.getLogger(CheckInController.class);

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private FacialRecognitionClient facialRecognition;


    @Autowired
    private ImageSetupService imageSetupService;


    @PostMapping("/facial-recognition/verify")
    public ResponseEntity<StaffVerifyDTO> verifyEmployeeByFacialRecognition(@Valid @RequestBody ImageVerifyVggDTO imageVerifyVggDTO) {

        logger.info("verifyEmployeeByFacialRecognition()");
        LocalDateTime currentDateTime = LocalDateTime.now();
        StaffVerifyDTO staffVerifyDTO =
                facialRecognition.verifyStaffByFacialRecognition(currentDateTime, imageVerifyVggDTO.getImageSetupDTO());

        return new ResponseEntity<>(staffVerifyDTO, HttpStatus.OK);
    }

}
