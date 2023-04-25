package com.example.ctsbe.repository;

import com.example.ctsbe.dto.complaint.ComplaintAddDTO;
import com.example.ctsbe.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    Page<Complaint> findByStatusOrderByCreatedDateAsc(String status, Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c where c.status like %:status% order by c.lastUpdated desc,c.createdDate desc")
    Page<Complaint> getListComplaintByStatus(String status, Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c order by c.lastUpdated desc,c.createdDate desc")
    Page<Complaint> getAllComplaint(Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c where c.staff.id =:id order by c.lastUpdated desc,c.createdDate desc ")
    Page<Complaint> getListByStaffId(int id, Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c where c.staff.id =:id and c.status =:status order by c.lastUpdated desc,c.createdDate desc ")
    Page<Complaint> getListByIdAndStatus(int id, String status, Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c where c.content like %:content%")
    Complaint getComplaintByContentContain(String content);
}
