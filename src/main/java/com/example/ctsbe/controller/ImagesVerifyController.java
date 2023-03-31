package com.example.ctsbe.controller;


import com.example.ctsbe.client.FacialRecognitionClient;
import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.dto.staff.StaffVerifyDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.dto.vgg.ImageVerifyVggDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.repository.StaffRepository;
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

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*")
public class ImagesVerifyController {

    private static final Logger logger = LoggerFactory.getLogger(ImagesVerifyController.class);

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private FacialRecognitionClient facialRecognition;

    @Autowired
    private ImageVerifyService imageVerifyService;


    @GetMapping("/image-verify")
    public ResponseEntity<Page<ImageVerifyDTO>> showImageVerify(@RequestParam Integer staffId,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) boolean onlyMe,
                                                                @RequestParam(required = false) boolean isError,
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
                                                                @RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "3") int size) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<ImageVerifyDTO> result = imageVerifyService.findImageVerify(staffId, name, Boolean.TRUE.equals(onlyMe), startTime, endTime, isError, pageable);
            Map<String, Object> response = new HashMap<>();
            response.put("listImage", result);
            response.put("currentPage", result.getNumber());
            response.put("allProducts", result.getTotalElements());
            response.put("allPages", result.getTotalPages());
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
