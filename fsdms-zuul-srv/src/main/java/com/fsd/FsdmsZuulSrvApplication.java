package com.fsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients(basePackages = "com.fsd.feigin")
@SpringBootApplication
public class FsdmsZuulSrvApplication {

  public static void main(String[] args) {
    SpringApplication.run(FsdmsZuulSrvApplication.class, args);
  }

}
