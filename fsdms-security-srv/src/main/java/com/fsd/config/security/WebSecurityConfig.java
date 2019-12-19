///**
// * @author: Jon
// * @create: 2019-10-25 15:39
// **/
//package com.fsd.config;
//
//import com.fsd.entity.UserEntity;
//import com.fsd.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//  @Autowired
//  UserService userService;
//
//  // Menthod 1: use PasswordEncoderFactories to get passwordEncoder(default using "bcrypt")
//  //      PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//  //      String passwordAfterEncoder = passwordEncoder.encode(password);
//  // Menthod 2: user BCryptPasswordEncoder directly
//  //      String passwordAfterEncoder = passwordEncoder().encode(password);
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return username -> {
//      UserEntity userEntity = userService.getUserByUserName(username);
//      if (userEntity==null) {
//        throw new UsernameNotFoundException("USER NAME NOT FOUND");
//      }
//      String password = userEntity.getPassword();
//      String role = userEntity.getRole();
//      log.debug("UserDetailsService-passwordAfterEncoder = {}", password);
//      log.debug("UserDetailsService-role = {}", role);
//      return User.withUsername(username).password(password).roles(role).build();
//    };
//  }
//
//  @Bean
//  public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
//    return new WebSecurityConfigurerAdapter() {
//      @Override
//      public void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable() // diable csrf
//                    // .csrf().csrfTokenRepository(csrfRepository()).and() // use CookieCsrfTokenRepository
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // use JWT，don't create session
//                    .and().authorizeRequests().antMatchers("/", "/index", "/login/**", "/guest/**").permitAll() // permits for unlogin users
//                    .and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN") // only allowed for role ADMIN
//                    .and().authorizeRequests().anyRequest().authenticated() // need authorize for all the others
//                    .and().formLogin() // enable form login
//                    .loginPage("/login"); // config login uri, if use customer login.html, may need viewResolver(e.g.: Thymeleaf)
//        // .successForwardUrl("/success") // forward url once login successfully and need use POST
//      }
//    };
//  }
//
//  public CookieCsrfTokenRepository csrfRepository() {
//    CookieCsrfTokenRepository csrfRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//    csrfRepository.setCookiePath("/");
//    return csrfRepository;
//  }
//
//}
//
//
