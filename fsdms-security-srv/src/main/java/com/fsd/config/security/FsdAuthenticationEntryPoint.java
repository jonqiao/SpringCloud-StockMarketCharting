/**
 * @author: Jon
 * @create: 2019-10-26 21:31
 **/
package com.fsd.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
public class FsdAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
    log.debug("AuthenticationEntryPoint: FOUND 401 Unauthorized");
    // This is invoked when user tries to access a secured REST resource without supplying any credentials
    // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
    response.sendError(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase());
  }

}
