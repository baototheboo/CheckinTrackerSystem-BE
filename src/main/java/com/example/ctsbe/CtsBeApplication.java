package com.example.ctsbe;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.entity.Role;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


@SpringBootApplication()
public class CtsBeApplication {


    public static void main(String[] args) {
        SpringApplication.run(CtsBeApplication.class, args);
        //System.out.println(Instant.from(Instant.now().atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE))));
        //StringUtil util = new StringUtil();
       // DateUtil dateUtil = new DateUtil();
       //System.out.println(dateUtil.cutStringDateToYearMonth("2023-04-01"));
    }

}
