package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.promotionLevel.PromotionLevelDTO;
import com.example.ctsbe.entity.PromotionLevel;

public class PromotionLevelMapper {
    public static PromotionLevelDTO convertLevelToLevelDTO(PromotionLevel level){
        PromotionLevelDTO dto = new PromotionLevelDTO(
                level.getId(),
                level.getName(),
                level.getDescription()
        );
        return dto;
    }
}
