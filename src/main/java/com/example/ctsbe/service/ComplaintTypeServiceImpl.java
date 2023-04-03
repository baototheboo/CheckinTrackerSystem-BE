package com.example.ctsbe.service;

import com.example.ctsbe.entity.ComplaintType;
import com.example.ctsbe.repository.ComplaintTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintTypeServiceImpl implements ComplaintTypeService{
    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;

    @Override
    public List<ComplaintType> getAllComplaintType() {
        return complaintTypeRepository.findAll();
    }
}
