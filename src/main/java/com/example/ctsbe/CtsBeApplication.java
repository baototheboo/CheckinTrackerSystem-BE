package com.example.ctsbe;

import com.example.ctsbe.entity.Role;
import com.example.ctsbe.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication()
public class CtsBeApplication {


    public static void main(String[] args) {
        SpringApplication.run(CtsBeApplication.class, args);
        /*DateUtil dateUtil = new DateUtil();

        System.out.println(dateUtil.convertStringToInstant("2001-05-19 00:00:00"));*/
    }

}
