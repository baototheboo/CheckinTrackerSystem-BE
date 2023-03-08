package com.example.ctsbe;

import com.example.ctsbe.entity.Role;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication()
public class CtsBeApplication {


    public static void main(String[] args) {
        SpringApplication.run(CtsBeApplication.class, args);
        /*StringUtil util = new StringUtil();
        System.out.println(util.cutStringRole("ROLE_HUMAN RESOURCE"));*/
    }

}
