package com.example.ctsbe.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ComplaintServiceImplTest {

    @Test
    void addComplaint() {
    }

    @Test
    void getListComplaint() {
    }

    @Test
    void updateComplaint() {
    }

    @Test
    void getListComplaintByStatus() {
    }

    @Test
    void getListComplaintById() {
    }

    @Test
    void getListComplaintByIdAndStatus() {
    }
}