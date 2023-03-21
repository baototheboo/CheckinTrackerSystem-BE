package com.example.ctsbe.service;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.dto.staff.RecognizedStaffDTO;
import com.example.ctsbe.dto.staff.StaffSetupDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.entity.ImagesSetup;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.enums.FaceStatus;
import com.example.ctsbe.repository.ImageSetupRepository;
import com.example.ctsbe.repository.ImageVerifyRepository;
import com.example.ctsbe.repository.StaffRepository;
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
import java.time.Instant;
import java.time.LocalDate;
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


    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ImageSetupRepository imageSetupRepository;

    @Autowired
    private ImageVerifyRepository imageVerifyRepository;

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
        String fullName = "";
        Float probability = 0F;
        Integer staffId = null;
        if (recognizedStaffDTO != null
                && !StringUtils.isEmpty(recognizedStaffDTO.getPartId())) {
            staffId = Integer.parseInt(recognizedStaffDTO.getPartId());
            Staff staff = staffRepository.findStaffById(staffId);
            fullName = staff != null ? staff.getFullName().trim().replace(" ", "_") : "";
            probability = recognizedStaffDTO.getProbability();
        }

        String today = LocalDate.now().toString();
        String relativePath = (probability != 0F ? successPath : errorPath) + today + "/";
        if (CollectionUtils.isEmpty(imageSetupVggDTO.getImgs())) {
            return null;
        }
        return saveImageToFolder(imageSetupVggDTO.getImgs(), relativePath,
                localDateTime, fullName, probability, staffId, false);
    }

    @Override
    public ImagesVerify saveImageToFolder(List<String> images, String relativePath, LocalDateTime localDateTime, String fullName, Float probability, Integer staffId, Boolean setupStaffImage) {
        String absolutePath = imagePath + relativePath;
        try {
            if (!Files.exists(Paths.get(absolutePath))) {
                Files.createDirectories(Paths.get(absolutePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ImageSetupDTO> imageSetupDTOs = new ArrayList<>();
        List<ImageVerifyDTO> imageVerifyDTOList = new ArrayList<>();
        for (String image : images) {
            byte[] data = Base64.getMimeDecoder().decode(image);
            localDateTime = localDateTime.plusSeconds(10);
            String strDate = localDateTime.toString().replace(":", "_");
            String fileName = fullName + "_" + strDate + "_" + (probability != null ? probability.toString() : "") + ".jpg";
            try {
                FileOutputStream fos = new FileOutputStream(new File(absolutePath + fileName));
                fos.write(data);
                Path pathImages = Paths.get(relativePath + fileName);
                if (probability != null) {
                    FaceStatus status = (probability * 100) == 0 ? FaceStatus.FAIL : (probability * 100) >= threshold ? FaceStatus.APPROVED : FaceStatus.PENDING;
                    ImageVerifyDTO imageVerifyDTO = new ImageVerifyDTO();
                    imageVerifyDTO.setName(fullName.replace("_", " "));
                    imageVerifyDTO.setImage(pathImages.toString());
                    imageVerifyDTO.setTimeVerify(localDateTime);
                    imageVerifyDTO.setProbability(probability.doubleValue());
                    imageVerifyDTO.setRecognizeStaffId(staffId);
                    imageVerifyDTO.setStatus(status);
                    imageVerifyDTOList.add(imageVerifyDTO);
                }
                String path = pathImages.toString().replace("\\", "/");
                ImageSetupDTO imageSetupDTO = new ImageSetupDTO(path, localDateTime, staffId, fullName, "OK", LocalDateTime.now(), LocalDateTime.now());
                imageSetupDTOs.add(imageSetupDTO);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (setupStaffImage) {
            Staff staff = staffRepository.findStaffById(staffId);
            List<ImagesSetup> imagesSetups = imageSetupDTOs.stream().map(ImageSetupDTO::toEntity).collect(Collectors.toList());
            for (ImagesSetup imagesSetup : imagesSetups) {
                imageSetupRepository.save(imagesSetup);
            }
            staff.setLastTrainedTime(Instant.now());
            staffRepository.save(staff);
        }
        if (CollectionUtils.isEmpty(imageVerifyDTOList)) {
            return null;
        }
        List<ImagesVerify> imageVerifies = imageVerifyDTOList.stream()
                .map(ImageVerifyDTO::toEntity).collect(Collectors.toList());
                imageVerifyRepository.saveAll(imageVerifies);
        return imageVerifies.get(0);
    }
}
