/**
 * @author: Jon
 * @create: 2019-10-28 17:03
 **/
package com.fsd.service;

public interface MailService {
  /**
   * send text mail
   *
   * @param to      receiver
   * @param subject subject
   * @param content content
   */
  void sendSimpleMail(String to, String subject, String content);

  void sendSimpleMail(String to, String subject, String content, String... cc);

  /**
   * send HTML mail
   *
   * @param to      receiver
   * @param subject subject
   * @param content content can have <html>
   */
  void sendHtmlMail(String to, String subject, String content);

  void sendHtmlMail(String to, String subject, String content, String... cc);

  /**
   * 发送带附件的邮件
   *
   * @param to       receiver
   * @param subject  subject
   * @param content  content
   * @param filePath Attachment
   */
  void sendAttachmentMail(String to, String subject, String content, String filePath);

  void sendAttachmentMail(String to, String subject, String content, String filePath, String... cc);

  /**
   * send with resource(img) content
   *
   * @param to      receiver
   * @param subject subject
   * @param content content
   * @param rscPath imgPath
   * @param rscId   imgID，used in <img> in order to display image
   */
  void sendResourceMail(String to, String subject, String content, String rscPath, String rscId);

  void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc);

}
