/**
 * @author: Jon
 * @create: 2019-10-27 03:53
 **/
package com.fsd.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@Component
public class FsdAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
    log.debug("AccessDeniedHandler: FOUND 403 Forbidden");
    response.sendError(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase());
  }

}
