package org.vinayM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Discovery_Server_Main {
    public static void main(String[] args) {
        SpringApplication.run(Discovery_Server_Main.class,args);
    }
}