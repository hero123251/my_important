package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class GovenEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovenEurekaApplication.class,args);
    }
}