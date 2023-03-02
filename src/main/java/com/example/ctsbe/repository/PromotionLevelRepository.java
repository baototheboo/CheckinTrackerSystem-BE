package com.example.ctsbe.repository;

import com.example.ctsbe.entity.PromotionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionLevelRepository extends JpaRepository<PromotionLevel,Integer> {
    PromotionLevel findById(int id);
}
