package com.example.ctsbe.service;

import com.example.ctsbe.dto.promotionLevel.PromotionLevelDTO;
import com.example.ctsbe.entity.PromotionLevel;
import com.example.ctsbe.repository.PromotionLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionLevelServiceImpl implements PromotionLevelService{
    @Autowired
    private PromotionLevelRepository promotionLevelRepository;
    @Override
    public PromotionLevel getPromotionLevelById(int id) {
        return promotionLevelRepository.findById(id);
    }

    @Override
    public List<PromotionLevel> getAllLevels() {
        return promotionLevelRepository.findAllLevel();
    }
}
