package com.kgc.kmall.kmalluserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kgc.kmall.kmalluserservice.mapper")
public class KmallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmallUserServiceApplication.class, args);
    }

}
