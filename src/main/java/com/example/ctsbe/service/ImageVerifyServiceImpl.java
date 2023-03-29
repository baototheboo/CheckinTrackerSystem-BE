package com.example.ctsbe.service;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.dto.staff.RecognizedStaffDTO;
import com.example.ctsbe.dto.staff.StaffSetupDTO;
import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.example.ctsbe.entity.*;
import com.example.ctsbe.enums.FaceStatus;
import com.example.ctsbe.exception.ImageNotFoundException;
import com.example.ctsbe.exception.StaffDoesNotExistException;
import com.example.ctsbe.repository.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageVerifyServiceImpl implements ImageVerifyService{

    @Value("D:/images")
    private String imagePath;

    @Value("80")
    private Float threshold;

    private LocalTime morningStart = LocalTime.of(8, 30, 0);
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ImageSetupRepository imageSetupRepository;

    @Autowired
    private ImageVerifyRepository imageVerifyRepository;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private AccountRepository accountRepository;


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
            if ((probability != 0F)&(!isCheckedInOrNot(staffId))) { //check-in
                Timesheet timesheet = new Timesheet();
                timesheet.setStaff(staff);
                timesheet.setDate(LocalDate.now());
                timesheet.setTimeCheckIn(LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant());
                timesheet.setDateStatus(!isLateOrNot()? "Ok" : "Late");
                timesheetRepository.save(timesheet);
            }
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
                    imageVerifyDTO.setTimeVerify(LocalDateTime.from(localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"))));
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

    @Override
    public Page<ImageVerifyDTO> findImageVerify(Integer staffId, String name, boolean onlyMe, LocalDate startDate, LocalDate endDate, boolean isError, Pageable pageable) {
        Page<ImageVerifyDTO> listImageVerify = null;

        if (startDate != null && endDate != null
                && startDate.isAfter(endDate)) {
            throw new ImageNotFoundException("Start date must less or equal than end date");
        }
        Optional<Staff> staff = staffRepository.findById(staffId);
        if (!staff.isPresent()) {
            throw new StaffDoesNotExistException(staffId);
        }
        Instant startTime = startDate != null ? startDate.atStartOfDay().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant() : LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        Instant endTime = endDate != null ? endDate.plusDays(1).atStartOfDay().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant() : LocalDateTime.now().plusDays(1).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        if (name == null) {
            name = "";
        }
        String role = accountRepository.getById(staffId).getRole().getRoleName();
        if (isError) {
            listImageVerify = imageVerifyRepository.findErrorByTimeVerify(startTime, endTime, FaceStatus.FAIL, pageable);
            }
        else if (onlyMe) {
            listImageVerify = imageVerifyRepository.findApprovedAndPendingByStaffIdAndTimeVerify(name, staffId, startTime, endTime, pageable);
        } else if (role.equals("ROLE_HUMAN RESOURCE")) {
                listImageVerify = imageVerifyRepository.findAllApprovedAndPendingByTimeVerify(name, startTime,
                        endTime, pageable);
        }

        return listImageVerify;
    }

    public boolean isLateOrNot(){
        LocalTime currentTime = LocalTime.now().minusSeconds(10);
        if (currentTime.isBefore(morningStart)) return false;
        else return true;
    }

    public boolean isCheckedInOrNot(Integer staffId){
        LocalDate today = LocalDate.now();
        List<Timesheet> timesheetList = timesheetRepository.findCheckedInStaff(staffId,today, "OK", "Late");
        if (!timesheetList.isEmpty()) return true;
        else return false;

    }
}
