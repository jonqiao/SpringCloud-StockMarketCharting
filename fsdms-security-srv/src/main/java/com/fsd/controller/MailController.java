/**
 * @author: Jon
 * @create: 2019-10-28 14:42
 **/
package com.fsd.controller;

import com.fsd.entity.UserEntity;
import com.fsd.service.MailService;
import com.fsd.service.UserService;
import com.fsd.utils.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/mail")
public class MailController {

  @Value("${spring.mail.maillink}")
  private String mailLink;

  @Autowired
  private UserService userService;

  @Autowired
  private MailService mailService;

  @RequestMapping("/sendmail/{email}")
  public ResponseEntity<ResponseBean> sendSimpleMail(@PathVariable("email") String email) throws Exception {
    try {
      String subject = "Notification from FSD-SBA";
      String content = "You have one notification from FSD-SBA, please check ASAP...";
      mailService.sendSimpleMail(email, subject, content);
      return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("MAIL SEND SUCCESSFULLY!"));
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
      return ResponseEntity.status(FORBIDDEN).body(new ResponseBean(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase()).error(e.getMessage()));
    }
  }

  @Scheduled(fixedRate = 60000)
  @RequestMapping("/sendmailauto")
  public void sendSimpleMailAuto() throws Exception {
    try {
      // 1. get user list from db where user.active == "N"
      UserEntity user = userService.getFirstByActive("N");
      if (user != null) {
        String email = user.getEmail();
        String subject = "Auto Notification from FSD-SBA";
        String content = "Please confirm your signup from FSD-SBA \n\n";
        String activeLink = mailLink + user.getUsername();
        log.debug("activelink = {}", activeLink);
        content = content + activeLink;
        mailService.sendSimpleMail(email, subject, content);
        // 2. update user.active = "S" after sending the mail
        userService.setActiveForUser(user.getUsername(), "S");
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
    }
  }

}
