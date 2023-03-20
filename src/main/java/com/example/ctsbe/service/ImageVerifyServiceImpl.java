package com.example.ctsbe.service;

import com.example.ctsbe.dto.staff.RecognizedStaffDTO;
import com.example.ctsbe.dto.staff.StaffSetupDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageVerifyServiceImpl implements ImageVerifyService{

    @Value("D:/images")
    private String imagePath;

    @Value("80")
    private Float threshold;

    @Value("${application.url.image}")
    private String urlImage;


    private static String errorPath = "/error-by-date/";

    private static String successPath = "/success-by-date/";

    private static String setupPath = "/setup/";
    @Override
    public void saveImageForSetup(List<String> images, Staff staff) {
        String fullName = staff != null ? staff.getFullName().trim().replace(" ", "_") : "";
        LocalDateTime timeSetup = LocalDateTime.now();
        if (!CollectionUtils.isEmpty(images)) {
            saveImageToFolder(images, setupPath, timeSetup, fullName, null, staff.getId(), true);
        }
    }

    @Override
    public ImagesVerify saveImageForVerify(ImageSetupVggDTO imageSetupVggDTO, LocalDateTime localDateTime, RecognizedStaffDTO recognizedStaffDTO) {
        return null;
    }

    @Override
    public ImagesVerify saveImageToFolder(List<String> images, String relativePath, LocalDateTime localDateTime, String fullName, Float probability, Integer staffId, Boolean setupEmployeeImage) {
        String absolutePath = imagePath + relativePath;
        try {
            if (!Files.exists(Paths.get(absolutePath))) {
                Files.createDirectories(Paths.get(absolutePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ImagesSetupDTO> imagesSetupDTOS = new ArrayList<>();
        List<ImageVerifyDTO> imageVerifyDTOList = new ArrayList<>();
        for (String image : images) {
            byte[] data = Base64.getMimeDecoder().decode(image);
            //localDateTime = localDateTime.plusSeconds(10);
            String strDate = localDateTime.toString().replace(":", "_");
            String fileName = fullName + "_" + strDate + "_" + (probability != null ? probability.toString() : "") + ".jpg";
            try {
                FileOutputStream fos = new FileOutputStream(new File(absolutePath + fileName));
                fos.write(data);
                Path pathImages = Paths.get(relativePath + fileName);
                if (probability != null) {
                    ClockDevice clockDevice = commonService.getCurrentClockDevice();
                    UUID clockDeviceId = null;
                    if (clockDevice != null) {
                        clockDeviceId = clockDevice.getId();
                    }
                    ClockStatus status = (probability * 100) == 0 ? ClockStatus.FAIL : (probability * 100) >= threshold ? ClockStatus.APPROVED : ClockStatus.PENDING;
                    FacialRecognitionStatus setupStatus = FacialRecognitionStatus.PENDING;
                    UUID actualEmployeeId = (!StringUtils.isEmpty(fullName) && status == ClockStatus.APPROVED) ? employeeId : null;
                    ImageVerifyDTO imageVerifyDTO = new ImageVerifyDTO.Builder()
                            .withName(fullName.replace("_", " "))
                            .withImage(pathImages.toString())
                            .withTimeVerify(localDateTime)
                            .withProbability(probability.doubleValue())
                            .withRecognizeEmployeeId(employeeId)
                            .withActualEmployeeId(actualEmployeeId)
                            .withClockStatus(status)
                            .withSetupStatus(setupStatus)
                            .widthRecognizeEmotion(emotion)
                            .withClockDeviceId(clockDeviceId)
                            .withImageDisplayed(true)
                            .build();
                    imageVerifyDTOList.add(imageVerifyDTO);
                }
                String path = pathImages.toString().replace("\\", "/");
                ImagesSetupDTO imagesSetupDTO = new ImagesSetupDTO(path, localDateTime, employeeId, fullName, true, LocalDateTime.now(), LocalDateTime.now());
                imagesSetupDTOS.add(imagesSetupDTO);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (setupEmployeeImage) {
            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            List<ImagesSetup> imagesSetups = imagesSetupDTOS.stream().map(ImagesSetupDTO::toEntity).collect(Collectors.toList());
            for (ImagesSetup imagesSetup : imagesSetups) {
                imagesSetupRepository.save(imagesSetup);
            }
            employee.setLastTrainedTime(LocalDateTime.now());
            employeeRepository.save(employee);
        }
        if (CollectionUtils.isEmpty(imageVerifyDTOList)) {
            return null;
        }
        List<ImageVerify> imageVerifies = imageVerifyDTOList.stream()
                .map(ImageVerifyDTO::toEntity).collect(Collectors.toList());
        imageVerifyRepository.saveAll(imageVerifies);
        return imageVerifies.get(0);
    }
}
