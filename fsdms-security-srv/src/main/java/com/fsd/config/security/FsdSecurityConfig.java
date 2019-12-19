/**
 * @author: Jon
 * @create: 2019-10-25 02:14
 **/
package com.fsd.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class FsdSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private FsdAuthenticationEntryPoint fsdAuthenticationEntryPoint;
  @Autowired
  private FsdAccessDeniedHandler fsdAccessDeniedHandler;
  @Autowired
  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors().and().csrf().disable() // diable csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // use JWT，don't create session
                .and().exceptionHandling().accessDeniedHandler(fsdAccessDeniedHandler).authenticationEntryPoint(fsdAuthenticationEntryPoint) //
                .and().authorizeRequests() // enable authorize HttpServletRequest
                .antMatchers("/", "/login/**", "/guest/**", "/actuator/**").permitAll() // permits for unlogin users
                .antMatchers("/api/v1/user/signup").permitAll() // permit for signup
                .antMatchers("/api/v1/user/active/**").permitAll() // permit for active user
                .antMatchers("/api/v1/security/signin").permitAll() // permit for signin
                .antMatchers("/api/v1/security/admin").hasRole("ADMIN") // only allowed for role ADMIN
                // .antMatchers("/api/v1/security/authenticated").hasAnyRole("ADMIN", "USER") // only allowed for roles "MENTEE", "MENTOR"
                .anyRequest().authenticated() // need authorize for all the others
                .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class) // JWT based security filter
                .headers().cacheControl(); // disable page caching
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    //allow Swagger URL to be accessed without authentication
    //    web.ignoring()
    //       .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");

    // for springfox v2.5+
    web.ignoring().antMatchers("/v2/api-docs", // swagger api json
                               "/swagger-resources/configuration/ui", // used to get operations
                               "/swagger-resources", // used to get URI of api-docs
                               "/swagger-resources/configuration/security", // security option
                               "/webjars/**", // swagger static files
                               "/swagger-ui.html");
  }

}
