package com.example.ctsbe.repository;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.entity.ImagesSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageSetupRepository extends JpaRepository<ImagesSetup, Integer> {

    @Query(value = "SELECT new com.example.ctsbe.dto.image.ImageSetupDTO(i.id, i.image, " +
            "i.timeSetup, i.staff.id, i.status, " +
            "s.firstName, s.surname) " +
            "FROM ImagesSetup AS i " +
            "INNER JOIN Staff AS s " +
            "ON (i.staff.id = s.id) " +
            "WHERE i.staff.id = :staffId " +
            "AND i.status = 'OK'")
    Page<ImageSetupDTO>findImageSetupByStaffId(@Param("staffId") Integer staffId, Pageable pageable);

    @Query(value = "SELECT i " +
            "FROM ImagesSetup AS i " +
            "WHERE i.staff.id = :staffId " +
            "AND i.status = 'OK'")
    List<ImagesSetup> findImageSetupByStaffId(@Param("staffId") Integer staffId);
}
