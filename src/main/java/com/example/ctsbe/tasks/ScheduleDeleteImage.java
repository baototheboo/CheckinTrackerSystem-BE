package com.example.ctsbe.tasks;


import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.repository.ImageVerifyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@EnableScheduling
public class ScheduleDeleteImage {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImageVerifyRepository imageVerifyRepository;

    @Value(ApplicationConstant.IMAGE_PATH)
    private String imagePath;

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanImageFailTimeTooLimit() throws IOException {

        String imagePathLocal = this.imagePath;
        List<ImagesVerify> listImageVerify = imageVerifyRepository.findByStatusAndTimeVerify(ApplicationConstant.TIME_OF_EXIST_IMAGE_FAIL);
        imageVerifyRepository.deleteAll(listImageVerify);
        for(ImagesVerify imageVerify : listImageVerify) {
            String absolutePath = imagePathLocal + imageVerify.getImage().replace("/","\\");
            try {
                Files.delete(Paths.get(absolutePath));
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

    }
}