package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Integer> {
}
