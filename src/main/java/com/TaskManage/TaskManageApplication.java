package com.TaskManage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.TaskManage.Entity")
public class TaskManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManageApplication.class, args);
    }
}
