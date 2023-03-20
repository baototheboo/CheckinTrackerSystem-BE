package com.example.ctsbe.controller;


import com.example.ctsbe.client.FacialRecognitionClient;
import com.example.ctsbe.dto.vgg.ImageVerifyVggDTO;
import com.example.ctsbe.dto.staff.StaffVerifyDTO;
import com.example.ctsbe.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/check-in")
@CrossOrigin(origins = "*")
public class CheckInController {

    private static final Logger logger = LoggerFactory.getLogger(CheckInController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private FacialRecognitionClient facialRecognition;


    @PostMapping("/facial-recognition/verify")
    public ResponseEntity<StaffVerifyDTO> verifyEmployeeByFacialRecognition(@Valid @RequestBody ImageVerifyVggDTO imageVerifyVggDTO) {

        logger.info("verifyEmployeeByFacialRecognition()");

        StaffVerifyDTO staffVerifyDTO =
                facialRecognition.verifyStaffByFacialRecognition(imageVerifyVggDTO.getCurrentDateTime(), imageVerifyVggDTO.getImageSetupDTO());

        return new ResponseEntity<>(staffVerifyDTO, HttpStatus.OK);
    }


}
