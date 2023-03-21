package com.example.ctsbe.repository;

import com.example.ctsbe.entity.ImagesSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageSetupRepository extends JpaRepository<ImagesSetup, Integer> {

}
