package com.example.ctsbe;

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


@SpringBootApplication()
public class CtsBeApplication {


    public static void main(String[] args) {
        SpringApplication.run(CtsBeApplication.class, args);
        //DateUtil util = new DateUtil();
        //System.out.println(Integer.parseInt(util.convertLocalDateToStringDay(LocalDate.of(2023,03,1))));
    }

}
