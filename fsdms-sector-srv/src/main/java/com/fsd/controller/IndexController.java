/**
 * @author: Jon
 * @create: 2019-10-13 02:16
 **/
package com.fsd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@CrossOrigin
@Controller
public class IndexController {
  //  private static Logger log = LoggerFactory.getLogger(IndexController.class);

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public String root() {
    log.trace("trace message...");
    log.debug("debug message...");
    log.info("info message...");
    log.warn("warn message...");
    log.error("error message...");
    return "Hello FSD & let's Go!";
  }

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index() {
    return "redirect:/";
  }

}
