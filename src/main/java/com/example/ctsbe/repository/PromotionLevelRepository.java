package com.example.ctsbe.repository;

import com.example.ctsbe.entity.PromotionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionLevelRepository extends JpaRepository<PromotionLevel,Integer> {
    PromotionLevel findById(int id);

    @Query(value = "SELECT p FROM PromotionLevel p WHERE TRIM(p.name) NOT IN ('HR', 'AD')")
    List<PromotionLevel> findAllLevel();
}
