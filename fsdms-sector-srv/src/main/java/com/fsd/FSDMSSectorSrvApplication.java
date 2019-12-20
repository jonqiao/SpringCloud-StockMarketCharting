/**
 * @author: Jon
 * @create: 2019-10-13 02:16
 **/
package com.fsd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableJpaAuditing
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.fsd.feigin")
@SpringBootApplication
public class FSDMSSectorSrvApplication {

  public static void main(String[] args) {
    SpringApplication.run(FSDMSSectorSrvApplication.class, args);
  }

}
