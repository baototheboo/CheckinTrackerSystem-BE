package com.example.ctsbe.controller;


import com.example.ctsbe.client.FacialRecognitionClient;
import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.entity.ImagesSetup;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.service.ImageSetupService;
import com.example.ctsbe.service.ImageVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/image/setup")
@CrossOrigin(origins = "*")
public class ImagesSetupController {

    private static final Logger logger = LoggerFactory.getLogger(ImagesSetupController.class);


}
