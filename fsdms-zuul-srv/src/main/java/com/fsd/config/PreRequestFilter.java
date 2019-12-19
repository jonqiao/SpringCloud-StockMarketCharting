/**
 * @author: Jon
 * @create: 2019-10-29 22:07
 **/
package com.fsd.config;

import com.fsd.feigin.SecurityFeignClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
public class PreRequestFilter extends ZuulFilter {

  private static final String SIGNUP_URI = "/signup"; // permit
  private static final String SIGNIN_URI = "/signin"; // permit
  //  private static final String SIGNOUT_URI = "/scs/signout";
  private static final String USER_ACTIVE_URI = "/user/active"; // permit
  private static final String ADMIN_URI = "/admin"; // verify admin token

  private static final String INVALID_TOKEN = "Invalid Token";

  @Autowired
  private SecurityFeignClient securityFeignClient;

  @Override
  public String filterType() {
    return PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return PRE_DECORATION_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();

    if (request.getRequestURI().indexOf(SIGNIN_URI) >= 0 || request.getRequestURI().indexOf(SIGNUP_URI) >= 0 || request.getRequestURI().indexOf(
        USER_ACTIVE_URI) >= 0) {
      log.debug("PreRequestFilter-getRequestURI: {}", request.getRequestURI());
      return false;
    }
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    // verify token before routing to the services
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String authHeader = request.getHeader("Authorization");
    log.debug("PreRequestFilter-run:Authorization = {}", authHeader);

    if (StringUtils.isNotBlank(authHeader)) {
      HttpStatus authChkStatus = INTERNAL_SERVER_ERROR;
      try {
        if (request.getRequestURI().indexOf(ADMIN_URI) >= 0) {
          authChkStatus = securityFeignClient.admin(authHeader).getStatusCode();
        } else {
          authChkStatus = securityFeignClient.authenticated(authHeader).getStatusCode();
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        String status = e.getMessage().substring(7, 10);
        if (StringUtils.isNumeric(status)) {
          authChkStatus = HttpStatus.valueOf(Integer.valueOf(status));
        }
      }
      log.debug("PreRequestFilter-run:authChkStatus = {}", authChkStatus.toString());

      if (authChkStatus.equals(OK)) {
        // router the request
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(OK.value());
        ctx.set("isSuccess", true);
      } else {
        // block the rquest
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(authChkStatus.value());
        ctx.setResponseBody(authChkStatus.getReasonPhrase());
        ctx.set("isSuccess", false);
      }
    } else {
      // block the rquest
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(403);
      ctx.setResponseBody(INVALID_TOKEN);
      ctx.set("isSuccess", false);
    }
    return null;
  }

}
