/**
 * @author: Jon
 * @create: 2019-10-13 02:16
 **/
package com.fsd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Slf4j
@CrossOrigin
@Controller
public class IndexController {
  //  private static Logger log = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public String root() {
    log.debug("debug message ====> reach root");
    return "reach root ====> Hello FSD & let's Go!";
  }

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index() {
    log.debug("debug message ====> reach index");
    return "redirect:/root";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login() {
    log.debug("debug message ====> reach login");
    return "login";
  }

  // 如果使用表单登陆, 作为 form action的目标, 必须使用POST方法
  @RequestMapping(value = "/login/form", method = RequestMethod.POST)
  @ResponseBody
  public String loginForm() {
    log.debug("debug message ====> reach loginForm");
    return "reach loginForm ====> Hello FSD & let's Go!";
  }

  // 如果使用表单登陆, loginPage().successForwardUrl("/success") 必须使用POST方法
  @RequestMapping(value = "/success", method = RequestMethod.POST)
  @ResponseBody
  public String success() {
    log.debug("debug message ====> reach success");
    return "reach success ====> Hello FSD & let's Go!";
  }

  @RequestMapping(value = "/guest", method = RequestMethod.GET)
  @ResponseBody
  public String guest() {
    log.debug("debug message ====> reach guest");
    return "reach guest ====> Hello FSD & let's Go!";
  }

}
