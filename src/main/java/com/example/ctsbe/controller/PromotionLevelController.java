package com.example.ctsbe.controller;

import com.example.ctsbe.dto.promotionLevel.PromotionLevelDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.PromotionLevel;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.mapper.PromotionLevelMapper;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.PromotionLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/levels")
@CrossOrigin(origins = "*")
public class PromotionLevelController {

    @Autowired
    private PromotionLevelService promotionLevelService;

    @GetMapping("/getAllLevels")
    public ResponseEntity<?> getAllLevels() {
        try {
            List<PromotionLevel> list = promotionLevelService.getAllLevels();

            List<PromotionLevelDTO> listDto = list.stream().
                    map(PromotionLevelMapper::convertLevelToLevelDTO).collect(Collectors.toList());

            return new ResponseEntity<>(listDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
