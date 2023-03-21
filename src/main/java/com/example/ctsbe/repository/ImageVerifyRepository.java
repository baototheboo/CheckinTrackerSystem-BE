package com.example.ctsbe.repository;

import com.example.ctsbe.entity.ImagesVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageVerifyRepository extends JpaRepository<ImagesVerify,Integer> {


}
