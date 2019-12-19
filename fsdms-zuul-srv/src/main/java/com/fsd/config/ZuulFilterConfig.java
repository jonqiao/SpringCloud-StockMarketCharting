/**
 * @author: Jon
 * @create: 2019-10-29 22:22
 **/
package com.fsd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulFilterConfig {

  @Bean
  public PreRequestFilter preRequestFilter() {
    return new PreRequestFilter();
  }

}
