package com.arhivator.arhwithsheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;


@SpringBootApplication
@EnableScheduling
public class ArhWithShedulerApplication {

    public static void main(String[] args) {
        File file=new File("/home/danil/Documents/test_arhives");
        System.out.println("path = "+file.getAbsolutePath());
        System.out.println("path = "+file.exists());
        SpringApplication.run(ArhWithShedulerApplication.class, args);
    }
}
