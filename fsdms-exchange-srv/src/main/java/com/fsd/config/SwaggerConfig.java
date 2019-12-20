/**
 * @author: Jon
 * @create: 2019-11-01 01:05
 **/
package com.fsd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Value(value = "${swagger.enabled}")
  private Boolean swaggerEnabled;

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo()) // Sets the api's meta information as included in the json ResourceListing response.
        .enable(swaggerEnabled) // disable on prod env
        .select() //
        .apis(RequestHandlerSelectors.basePackage("com.fsd.controller")) //
        .paths(PathSelectors.any()) //
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder() // Builds the api information
                                .title("FSD Microservice API") // title for the API
                                .description("FSD Microservice...") // api description
                                .termsOfServiceUrl("") // url to the terms of service
                                .contact(new Contact("Jon", "https://www.xxx.com", "jon@xx.com")) // contact information
                                .version("0.0.1-SNAPSHOT") //
                                .build();
  }

}
