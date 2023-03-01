package com.example.ctsbe;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication()
public class CtsBeApplication {


    public static void main(String[] args) {
        SpringApplication.run(CtsBeApplication.class, args);
    }

}
