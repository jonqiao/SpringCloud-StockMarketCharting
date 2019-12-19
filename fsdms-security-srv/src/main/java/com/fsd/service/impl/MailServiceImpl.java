/**
 * @author: Jon
 * @create: 2019-10-28 17:11
 **/
package com.fsd.service.impl;

import com.fsd.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

  @Value("${spring.mail.username}")
  private String from;

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public void sendSimpleMail(String to, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);
    message.setFrom(from);

    javaMailSender.send(message);
  }

  @Override
  public void sendSimpleMail(String to, String subject, String content, String... cc) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setCc(cc);
    message.setSubject(subject);
    message.setText(content);

    javaMailSender.send(message);
  }

  @Override
  public void sendHtmlMail(String to, String subject, String content) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;

    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true); // set true to srupport html

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void sendHtmlMail(String to, String subject, String content, String... cc) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;

    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setCc(cc);
      helper.setSubject(subject);
      helper.setText(content, true);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void sendAttachmentMail(String to, String subject, String content, String filePath) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      FileSystemResource file = new FileSystemResource(new File(filePath));
      String fileName = file.getFilename();
      helper.addAttachment(fileName, file);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void sendAttachmentMail(String to, String subject, String content, String filePath, String... cc) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setCc(cc);
      helper.setSubject(subject);
      helper.setText(content, true);
      FileSystemResource file = new FileSystemResource(new File(filePath));
      String fileName = file.getFilename();
      helper.addAttachment(fileName, file);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      FileSystemResource res = new FileSystemResource(new File(rscPath));
      helper.addInline(rscId, res);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setCc(cc);
      helper.setSubject(subject);
      helper.setText(content, true);
      FileSystemResource res = new FileSystemResource(new File(rscPath));
      helper.addInline(rscId, res);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }

}
