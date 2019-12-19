package com.fsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class FSDMSEurekaSrvApplication {

  public static void main(String[] args) {
    SpringApplication.run(FSDMSEurekaSrvApplication.class, args);
  }

}
