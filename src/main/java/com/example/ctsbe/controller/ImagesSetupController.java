package com.example.ctsbe.controller;


import com.example.ctsbe.client.FacialRecognitionClient;
import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.entity.ImagesSetup;
import com.example.ctsbe.entity.Staff;
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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/image-setup")
@CrossOrigin(origins = "*")
public class ImagesSetupController {

    private static final Logger logger = LoggerFactory.getLogger(ImagesSetupController.class);

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private FacialRecognitionClient facialRecognition;

    @Autowired
    private ImageSetupService imageSetupService;

    @PostMapping("/setup/{staffId}")
    public ResponseEntity<String> setupEmployeeForFacialRecognition(@PathVariable String staffId,
                                                                    @Valid @RequestBody ImageSetupVggDTO imageSetupVggDTO) {

        Staff staff = staffRepository.findAvailableStaffByStaffId(Integer.parseInt(staffId));

//        List<FacialRecognitionStatus> facialRecognitionStatuses =
//                Arrays.asList(FacialRecognitionStatus.PENDING, FacialRecognitionStatus.TRAINED);
//
//        if (facialRecognitionStatuses.contains(staff.getFacialRecognitionStatus())) {
//            throw new SetupEmployeeFacialRecognitionException(staffId);
//        }
        String result = facialRecognition.setupStaffForFacialRecognition(staff, imageSetupVggDTO.getImgs());
        if (result.equals("OK")) {
            imageSetupService.saveImageForSetup(imageSetupVggDTO.getImgs(), staff);
        }
//        employeeService.updateFacialRecognitionStatus(accountEmployee.getEmployee(), FacialRecognitionStatus.PENDING);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/remove-staff-setup/{staffId}")
    public ResponseEntity<Void> removeStaffSetup(@PathVariable int staffId) {
        imageSetupService.removeStaffSetup(staffId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
