package com.example.ctsbe.repository;

import com.example.ctsbe.dto.image.ImageVerifyDTO;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.enums.FaceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ImageVerifyRepository extends JpaRepository<ImagesVerify,Integer> {
    String sharedSearchClause = "(:name = '' " +
            "OR lower(s.firstName) LIKE lower(concat('%',:name ,'%')) " +
            "OR lower(s.surname) LIKE lower(concat('%',:name ,'%')) " +
            "OR lower(s.email) LIKE lower(concat('%',:name ,'%'))) " +
            "AND (:startTime <= iv.timeVerify) " +
            "AND (iv.timeVerify <= :endTime)" +
            "ORDER BY iv.timeVerify desc";

    String filterByTimeVerify = "AND (:startTime <= iv.timeVerify) " +
            "AND (iv.timeVerify <= :endTime) " +
            "ORDER BY iv.timeVerify desc";

    @Query(value = "SELECT new com.example.ctsbe.dto.image.ImageVerifyDTO(iv.id, " +
            "iv.image, iv.timeVerify, iv.probability, " +
            "iv.recognizeStaffId, iv.status) " +
            "FROM ImagesVerify AS iv " +
            "WHERE iv.status = :status " + filterByTimeVerify)
    Page<ImageVerifyDTO> findErrorByTimeVerify(@Param("startTime") Instant startTime,
                                               @Param("endTime") Instant endTime,
                                               @Param("status") FaceStatus status,
                                               Pageable pageable);

    @Query(value = "SELECT new com.example.ctsbe.dto.image.ImageVerifyDTO(iv.id, " +
            "s.firstName, s.surname, iv.image, " +
            "iv.timeVerify, iv.probability, " +
            " iv.recognizeStaffId, iv.status) " +
            "FROM ImagesVerify AS iv " +
            "INNER JOIN Staff AS s ON (iv.recognizeStaffId = s.id)" +
            "WHERE :staffId = iv.recognizeStaffId " +
            "AND ((iv.status = 'PENDING')" +
            "OR (iv.status = 'APPROVED'))" +
            "AND" + sharedSearchClause)
    Page<ImageVerifyDTO> findApprovedAndPendingByStaffIdAndTimeVerify(@Param("name") String name,
                                                                    @Param("staffId") Integer staffId,
                                                                    @Param("startTime") Instant startTime,
                                                                    @Param("endTime") Instant endTime,
                                                                    Pageable pageable);

    @Query(value = "SELECT new com.example.ctsbe.dto.image.ImageVerifyDTO(iv.id, " +
            "s.firstName, s.surname, iv.image, " +
            "iv.timeVerify, iv.probability, " +
            " iv.recognizeStaffId, iv.status) " +
            "FROM ImagesVerify AS iv " +
            "INNER JOIN Staff AS s ON (iv.recognizeStaffId = s.id)" +
            "WHERE ((iv.status = 'PENDING')" +
            "OR (iv.status = 'APPROVED'))" +
            "AND" + sharedSearchClause)
    Page<ImageVerifyDTO> findAllApprovedAndPendingByTimeVerify(@Param("name") String name,
                                                               @Param("startTime") Instant startTime,
                                                               @Param("endTime") Instant endTime,
                                                               Pageable pageable);

    @Query(value = "SELECT iv FROM ImagesVerify AS iv " +
            "INNER JOIN Staff AS s ON s.id = iv.recognizeStaffId " +
            "WHERE (:startTime <= iv.timeVerify) " +
            "AND (iv.timeVerify <= :endTime) " +
            "AND ((iv.status = 'APPROVED') " +
            "OR (iv.status = 'PENDING')) " +
            "ORDER BY iv.timeVerify ASC")
    List<ImagesVerify> findImagesVerifiesApprovedAndPending(@Param("startTime") Instant startTime,
                                                            @Param("endTime") Instant endTime);

}
