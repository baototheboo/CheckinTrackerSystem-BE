package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    Page<Complaint> findByStatusOrderByCreatedDateAsc(String status, Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c where c.staff.id =:id order by c.createdDate asc")
    Page<Complaint> getListById(int id, Pageable pageable);

    @Query(value = "SELECT c FROM Complaint c where c.staff.id =:id and c.status =:status order by c.createdDate asc")
    Page<Complaint> getListByIdAndStatus(int id,String status, Pageable pageable);
}
