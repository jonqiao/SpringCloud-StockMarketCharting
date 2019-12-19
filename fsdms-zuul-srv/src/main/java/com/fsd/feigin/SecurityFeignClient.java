/**
 * @author: Jon
 * @create: 2019-10-29 03:11
 **/
package com.fsd.feigin;

import com.fsd.utils.ResponseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "fsdms-security-srv")
public interface SecurityFeignClient {

  // verify admin role
  @RequestMapping(value = "/fsdms/api/v1/security/admin", method = RequestMethod.GET)
  ResponseEntity<ResponseBean> admin(@RequestHeader(name = "Authorization") String authHeader);

  // verify token
  @RequestMapping(value = "/fsdms/api/v1/security/authenticated", method = RequestMethod.GET)
  ResponseEntity<ResponseBean> authenticated(@RequestHeader(name = "Authorization") String authHeader);

}
