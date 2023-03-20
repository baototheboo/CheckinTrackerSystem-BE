package com.example.ctsbe.client;

import com.example.ctsbe.config.FacialRecognitionConfiguration;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.dto.staff.RecognizedStaffDTO;
import com.example.ctsbe.dto.staff.StaffVerifyDTO;
import com.example.ctsbe.dto.staff.StaffSetupDTO;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.exception.FacialRecognitionIdentifyException;
import com.example.ctsbe.exception.ValidationException;
import com.example.ctsbe.service.ImageVerifyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FacialRecognitionClientImpl implements FacialRecognitionClient{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FacialRecognitionConfiguration facialRecognitionConfiguration;

    @Value("http://localhost:5001")
    private String applicationUrl;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ImageVerifyService imageVerifyService;


    public FacialRecognitionClientImpl(FacialRecognitionConfiguration facialRecognitionConfiguration) {
        this.facialRecognitionConfiguration = facialRecognitionConfiguration;
    }

    @Override
    public String setupStaffForFacialRecognition(Staff staff, List<String> imgs) {
        RestTemplate restTemplate = new RestTemplate();
        Long secondsTime = (new Date().getTime())/1000;
        try {
            return restTemplate.postForObject(
                    facialRecognitionConfiguration.getFacialRecognitionUri() + "/setup-staff",
                    new StaffSetupDTO(staff.getId(), staff.getStaffName(), imgs),
                    String.class);
        } catch (Exception e) {
            logger.error("setupEmployeeForFacialRecognition(): {}", e.getMessage());
            throw new ValidationException("Employee facial recognition setup failed.");
        }
    }

    @Override
    public StaffVerifyDTO verifyStaffByFacialRecognition(LocalDateTime currentDateTime, ImageSetupVggDTO imageSetupVggDTO){
        RecognizedStaffDTO recognizedStaffDTO = new RecognizedStaffDTO();
        RestTemplate restTemplate = new RestTemplate();
        ImagesVerify verifiedImage;
        Boolean showMessage = false;
        try {
            String result = restTemplate.postForObject(
                    facialRecognitionConfiguration.getFacialRecognitionUri() + "/verify-employee",
                    imageSetupVggDTO,
                    String.class);
            recognizedStaffDTO = objectMapper.readValue(result, RecognizedStaffDTO.class);
            verifiedImage = imageVerifyService.saveImageForVerify(imageSetupVggDTO,currentDateTime,recognizedStaffDTO);
        } catch (Exception e) {
            logger.error("verifyEmployeeByFacialRecognition(): {}", e.getMessage());
            imageVerifyService.saveImageForVerify(imageSetupVggDTO,currentDateTime,recognizedStaffDTO);
            throw new FacialRecognitionIdentifyException();
        }


        RecognizedStaffDTO recognizedStaffDTOs = this.getRecognisedEmployeeDTOFromFacialRecognition(imageSetupVggDTO);
        String partId = recognizedStaffDTOs.getPartId();
        if (StringUtils.isEmpty(partId)) {
            logger.error("GG_ERROR_FACIAL_RECOGNITION employeeId was empty");
            throw new FacialRecognitionIdentifyException();
        }
        UUID employeeId = UUID.fromString(partId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Optional<AccountEmployee> accountEmployeeOptional =
                accountEmployeeRepository.findByAccount_AccountIdAndEmployee_EmployeeId(accountId, employeeId);
        if (!accountEmployeeOptional.isPresent()) {
            logger.error("GG_ERROR_FACIAL_RECOGNITION employeeId does not belong to account {}", accountId);
            throw new FacialRecognitionIdentifyException();
        }
        AccountEmployee accountEmployee = accountEmployeeOptional.get();
        logger.info("GG_EMPLOYEE_RECOGNISED for account {} with probability {}", accountEmployee.getAccount().getAccountName(), recognizedStaffDTO.getProbability());

        if (Boolean.FALSE.equals(employeeService.isProbabilityHigherThanThreshold(recognizedStaffDTO.getProbability()))) {
            logger.error("GG_ERROR_FACIAL_RECOGNITION threshold not met. The probability was {}", recognizedStaffDTO.getProbability());
            showMessage = true;
        }
        if(employee == null){
            logger.error("GG_ERROR_FACIAL_RECOGNITION employeeId does not exist {}", employeeId);
            throw new FacialRecognitionIdentifyException();
        }
        StaffVerifyDTO staffVerifyDTO = employeeService.getPunchClockEmployeeDTO(accountEmployee.getAccount(),
                accountEmployee, currentDateTime, true);
        staffVerifyDTO.setProbability(recognizedStaffDTO.getProbability());
        staffVerifyDTO.setImageVerifyId(verifiedImage != null ? verifiedImage.getImageVerifyId() : null);
        staffVerifyDTO.setStaffId(employeeId);
        return staffVerifyDTO;
    }
}
