package org.example.bsproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"org.example.bsproject.mapper"})
@EnableTransactionManagement //启动事务
public class BSprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BSprojectApplication.class, args);
    }

}
