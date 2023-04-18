package com.example.ctsbe.service;

import com.example.ctsbe.constant.ApplicationConstant;
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
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.JwtTokenUtil;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.text.DecimalFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageVerifyServiceImpl implements ImageVerifyService{

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Value(ApplicationConstant.IMAGE_PATH)
    private String imagePath;

    @Value(ApplicationConstant.IMAGE_THRESHOLD)
    private Float threshold;

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

    @Override
    public ImagesVerify saveImageForVerify(ImageSetupVggDTO imageSetupVggDTO, LocalDateTime localDateTime, RecognizedStaffDTO recognizedStaffDTO) {
        LocalDateTime currentTime = localDateTime;
        String fullName = "";
        Float probability = 0F;
        Integer staffId = null;
        if (recognizedStaffDTO != null
                && !StringUtils.isEmpty(recognizedStaffDTO.getPartId())) {
            staffId = Integer.parseInt(recognizedStaffDTO.getPartId());
            Staff staff = staffRepository.findAvailableStaffByStaffId(staffId);
            fullName = staff != null ? staff.getFullName().trim().replace(" ", "_") : "";
            probability = recognizedStaffDTO.getProbability();
            if (probability != 0F){
                if (!isCheckedInOrNot(staffId, currentTime)){ //Kiểm tra đã check-in chưa. nếu chưa thì tiến hành xử lý check-in
                    Timesheet timesheet = new Timesheet();
                    timesheet.setStaff(staff);
                    timesheet.setDate(LocalDate.now());
                    timesheet.setTimeCheckIn(DateUtil.convertLocalDateTimeToInstant(currentTime));
                    if (!isLateMorningOrNot(currentTime)){ // kiểm tra đi muộn
                        timesheet.setDateStatus("OK");
                    } else
                    {
                        timesheet.setDateStatus("LATE");
                        long lateMinutes = Duration.between(LocalDateTime.of(LocalDate.now(), ApplicationConstant.MORNING_START) , currentTime).toMinutes();
                        timesheet.setNote("Muộn "+lateMinutes+" phút");
                    }
                    timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_ABSENT); //check-in xong mặc định chưa có tổng thời gian làm việc
                    timesheetRepository.save(timesheet);
                } else { // xử lý check-out
                    Timesheet timesheet = timesheetRepository.findCheckedInStaff(staffId, localDateTime.toLocalDate(), "OK", "LATE");
                    timesheet.setTimeCheckOut(DateUtil.convertLocalDateTimeToInstant(currentTime));
                    if (Objects.equals(timesheet.getDateStatus(), "OK")) { //check-in đúng giờ
                        if (!isEarlyAfternoonOrNot(currentTime)){
                            timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_DEFAULT); //trường hợp check out đứng giờ, giờ làm việc mặc định 8 tiếng
                            timesheet.setNote("Check-in sáng đúng giờ, Check-out chiều đúng giờ");
                        } else if (currentTime.toLocalTime().isBefore(ApplicationConstant.MORNING_START)){
                            timesheet.setWorkingHours(0.0); // check-out trước giờ làm việc sáng, không tính công ngày hôm đó.
                            timesheet.setNote("Check-in sáng đúng giờ, Check-out sáng trước giờ làm");
                        }  else if (currentTime.toLocalTime().isBefore(ApplicationConstant.MORNING_END)){
                            long morningWorkingHours = Duration.between(ApplicationConstant.MORNING_START, currentTime.toLocalTime()).toSeconds();
                            if (morningWorkingHours >= 0) {
                                timesheet.setWorkingHours(toHours((double) morningWorkingHours)); // check-out trước giờ quy định buổi sáng, giờ làm việc tính từ giờ mặc định của buổi sáng đến khi check-out
                                timesheet.setNote("Check-in sáng đúng giờ, Check-out sáng sớm hơn quy định");
                            }
                        }else if ((currentTime.toLocalTime().isBefore(ApplicationConstant.AFTERNOON_START))){
                                timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_MORNING); // check-out trước buổi chiều, giờ làm việc tính theo giờ mặc định buổi sáng.
                                timesheet.setNote("Check-in sáng đúng giờ, Check-out sáng");
                        } else {
                            long afternoonWorkingHours = Duration.between(ApplicationConstant.AFTERNOON_START, currentTime.toLocalTime()).toSeconds();
                            if (afternoonWorkingHours >= 0) {
                                double totalWorkingHours = ApplicationConstant.WORKING_HOURS_MORNING + toHours((double) afternoonWorkingHours);
                                timesheet.setWorkingHours(totalWorkingHours); // check-out sớm, giờ làm việc tính theo giờ mặc định buổi sáng và giờ làm thực tế buổi chiều.
                                timesheet.setNote("Check-in sáng đúng giờ, Check-out chiều sớm hơn quy định");
                            }
                        }
                    } else if (timesheet.getTimeCheckIn().isAfter(DateUtil.convertLocalDateTimeToInstant(LocalDateTime.of(LocalDate.now(), //check-in chiều muộn
                                                        ApplicationConstant.AFTERNOON_START)))) {
                        if (!isEarlyAfternoonOrNot(currentTime)){
                            long afternoonWorkingHours = Duration.between((timesheet.getTimeCheckIn().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalTime()),
                                                                        ApplicationConstant.AFTERNOON_END).toSeconds();
                            if (afternoonWorkingHours >= 0) {
                                timesheet.setWorkingHours(toHours((double) afternoonWorkingHours)); // check-out đúng giờ, giờ làm việc tính theo từ lúc check-in đến cuối buổi chiều.
                                timesheet.setNote("Check-in chiều muộn, Check-out chiều đúng giờ");
                            }
                        } else {
                            long afternoonWorkingHours = Duration.between((timesheet.getTimeCheckIn().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalTime()),
                                                                            currentTime.toLocalTime()).toSeconds();
                            if (afternoonWorkingHours >= 0) {
                                timesheet.setWorkingHours(toHours((double) afternoonWorkingHours)); // check-out sớm, giờ làm việc tính theo giờ làm thực tế buổi chiều.
                                timesheet.setNote("Check-in chiều muộn, Check-out chiều sớm hơn quy định");
                            }
                        }
                    } else if (timesheet.getTimeCheckIn().isAfter(DateUtil.convertLocalDateTimeToInstant(LocalDateTime.of(LocalDate.now(), //check-in đúng giờ buổi chiều
                                                            ApplicationConstant.MORNING_END)))) {
                        if (!isEarlyAfternoonOrNot(currentTime)){
                                timesheet.setWorkingHours(ApplicationConstant.WORKING_HOURS_AFTERNOON); // check-out đúng giờ, giờ làm việc tính theo từ lúc check-in đến cuối buổi chiều.
                                timesheet.setNote("Check-in chiều đúng giờ, Check-out chiều đúng giờ");
                        } else if (currentTime.toLocalTime().isBefore(ApplicationConstant.AFTERNOON_START)){
                                timesheet.setWorkingHours(0.0); // check-out trước giờ chiều, không tính công ngày hôm đó.
                                timesheet.setNote("Check-in chiều đúng giờ, Check-out chiều trước giờ làm");
                        } else {
                            long afternoonWorkingHours = Duration.between(ApplicationConstant.AFTERNOON_START,
                                                                    currentTime.toLocalTime()).toSeconds();
                            if (afternoonWorkingHours >= 0) {
                                timesheet.setWorkingHours(toHours((double) afternoonWorkingHours)); // check-out sớm, giờ làm việc tính từ đầu giờ chiều đến lúc check-out.
                                timesheet.setNote("Check-in chiều đúng giờ, Check-out chiều sớm hơn quy định");
                            }
                        }
                    } else if (timesheet.getTimeCheckIn().isAfter(DateUtil.convertLocalDateTimeToInstant(LocalDateTime.of(LocalDate.now(),
                                                            ApplicationConstant.MORNING_START)))) { // check-in sáng muộn.
                        if (!isEarlyAfternoonOrNot(currentTime)){
                            long morningWorkingHours = Duration.between((timesheet.getTimeCheckIn().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalTime()),
                                    ApplicationConstant.MORNING_END).toSeconds();
                            if (morningWorkingHours >= 0) {
                                double totalWorkingHours = toHours((double) morningWorkingHours) + ApplicationConstant.WORKING_HOURS_AFTERNOON;
                                timesheet.setWorkingHours(totalWorkingHours); // check-out đúng giờ, giờ làm việc tính theo giờ làm thực tế buổi sáng và giờ làm mặc định buổi chiều.
                                timesheet.setNote("Check-in sáng muộn, Check-out chiều đúng giờ");
                            }
                        } else if (currentTime.toLocalTime().isBefore(ApplicationConstant.MORNING_END)){
                            long morningWorkingHours = Duration.between((timesheet.getTimeCheckIn().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalTime()),
                                                                    currentTime.toLocalTime()).toSeconds();
                            if (morningWorkingHours >= 0) {
                                timesheet.setWorkingHours(toHours((double) morningWorkingHours)); // check-out trước giờ quy định buổi sáng, thời gian làm việc tính theo thời gian làm việc thực tế.
                                timesheet.setNote("Check-in sáng muộn, Check-out sáng sớm hơn quy định");
                            }
                        } else if (currentTime.toLocalTime().isBefore(ApplicationConstant.AFTERNOON_START)){
                            long morningWorkingHours = Duration.between((timesheet.getTimeCheckIn().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalTime()),
                                    ApplicationConstant.MORNING_END).toSeconds();
                            if (morningWorkingHours >=0)  {
                                timesheet.setWorkingHours(toHours(morningWorkingHours)); // check-out sáng đúng giờ, giờ làm việc tính theo thời gian làm việc thực tế buổi sáng.
                                timesheet.setNote("Check-in sáng muộn, Check-out sáng đúng giờ");
                            }
                        }else if (currentTime.toLocalTime().isBefore(ApplicationConstant.AFTERNOON_END)){
                            long morningWorkingHours = Duration.between((timesheet.getTimeCheckIn().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalTime()),
                                                                ApplicationConstant.MORNING_END).toSeconds();
                            long afternoonWorkingHours = Duration.between(ApplicationConstant.AFTERNOON_START,
                                                                currentTime.toLocalTime()).toSeconds();
                            if ((afternoonWorkingHours >= 0)&&(morningWorkingHours >=0))  {
                                double totalWorkingHours = ((double) morningWorkingHours) + (double) afternoonWorkingHours;
                                timesheet.setWorkingHours(toHours(totalWorkingHours)); // check-out sớm, giờ làm việc tính theo thời gian làm việc thực tế.
                                timesheet.setNote("Check-in sáng muộn, Check-out chiều sớm hơn quy định");
                            }
                        }
                    }
                    timesheetRepository.save(timesheet);
                }

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
                    imageVerifyDTO.setTimeVerify(LocalDateTime.from(localDateTime.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE))));
                    imageVerifyDTO.setProbability(probability.doubleValue());
                    imageVerifyDTO.setRecognizeStaffId(staffId);
                    imageVerifyDTO.setStatus(status);
                    imageVerifyDTOList.add(imageVerifyDTO);
                }
                String path = pathImages.toString().replace("\\", "/");
                ImageSetupDTO imageSetupDTO = new ImageSetupDTO(path, localDateTime, staffId, fullName, "OK", localDateTime, LocalDateTime.now());
                imageSetupDTOs.add(imageSetupDTO);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (setupStaffImage) {
            Staff staff = staffRepository.findAvailableStaffByStaffId(staffId);
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
        Page<ImageVerifyDTO> listImageVerify = new PageImpl<>(Collections.emptyList(), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), 0);

        if (startDate != null && endDate != null
                && startDate.isAfter(endDate)) {
            throw new ImageNotFoundException("Ngày bắt đầu không được sau ngày kết thúc. Hãy thử lại!");
        }
        Optional<Staff> staff = staffRepository.findById(staffId);
        if (!staff.isPresent()) {
            throw new StaffDoesNotExistException(staffId);
        }
        Instant startTime = startDate != null ? DateUtil.convertLocalDateTimeToInstant(startDate.atStartOfDay()) : DateUtil.convertLocalDateTimeToInstant(LocalDate.now().atStartOfDay());
        Instant endTime = endDate != null ? DateUtil.convertLocalDateTimeToInstant(endDate.plusDays(1).atStartOfDay().minusSeconds(1)) : DateUtil.convertLocalDateTimeToInstant(LocalDate.now().atStartOfDay().plusDays(1).minusSeconds(1));
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

    public boolean isLateMorningOrNot(LocalDateTime localDateTime){
        LocalTime currentTime = localDateTime.toLocalTime().minusSeconds(10);
        if (currentTime.isBefore(ApplicationConstant.MORNING_START)) return false;
        else return true;
    }
    public boolean isEarlyAfternoonOrNot(LocalDateTime localDateTime){
        LocalTime currentTime = localDateTime.toLocalTime();
        if (currentTime.isAfter(ApplicationConstant.AFTERNOON_END)) return false;
        else return true;
    }

    public boolean isCheckedInOrNot(Integer staffId, LocalDateTime localDateTime){
        LocalDate today = localDateTime.toLocalDate();
        Timesheet timesheet = timesheetRepository.findCheckedInStaff(staffId,today, "OK", "LATE");
        if (!(timesheet == null)) return true;
        else return false;
    }

    public double toHours(double seconds){
        DecimalFormat df = new DecimalFormat("#.#");
        double hours = seconds/3600;
        return Double.parseDouble(df.format(hours));
    }
}
