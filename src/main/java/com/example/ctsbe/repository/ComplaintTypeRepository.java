package com.example.ctsbe.repository;

import com.example.ctsbe.entity.ComplaintType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTypeRepository extends JpaRepository<ComplaintType,Integer> {
}
