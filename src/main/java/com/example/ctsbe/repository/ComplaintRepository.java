package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    Page<Complaint> findByStatus(String status, Pageable pageable);
}
