package com.fsd.controller; /**
 * @author: Jon
 * @create: 2019-12-09 21:29
 **/

import com.fsd.feigin.CompanyFeignClient;
import com.fsd.model.ExchangeDtls;
import com.fsd.service.ExchangeService;
import com.fsd.utils.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeControllerTest {

  private ExchangeController exchangeController;

  @Autowired
  private ExchangeService exchangeService;

  @Autowired
  private CompanyFeignClient companyFeignClient;

  @Before
  public void init() {
    exchangeController = new ExchangeController(exchangeService, companyFeignClient);
  }

  @Test
  @Transactional
  public void getExchangeAll() {
    try {
      ResponseEntity<ResponseBean> response = exchangeController.getExchangeAll();
      log.debug("response = {}", response.getStatusCodeValue());
      assertEquals(200, response.getStatusCodeValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Test
  public void getExchangeByStockExchange() {
    try {
      ResponseEntity<ResponseBean> response = exchangeController.getExchangeByStockExchange("BSE");
      assertEquals(200, response.getStatusCodeValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Test
  public void newExchange() {
    try {
      ExchangeDtls exchangeDtls = new ExchangeDtls();
      exchangeDtls.setStockExchange("AAA");
      exchangeDtls.setBrief("TEST-brief");
      exchangeDtls.setContactAddress("TEST-contactAddress");
      exchangeDtls.setRemarks("TEST-remarks");

      ResponseEntity<ResponseBean> response = exchangeController.newExchange(exchangeDtls);
      assertEquals(200, response.getStatusCodeValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Test
  public void updateExchangeByStockExchange() {
    try {
      ExchangeDtls exchangeDtls = new ExchangeDtls();
      exchangeDtls.setStockExchange("BSE");
      exchangeDtls.setBrief("TEST-brief");
      exchangeDtls.setContactAddress("TEST-contactAddress");
      exchangeDtls.setRemarks("TEST-remarks");

      ResponseEntity<ResponseBean> response = exchangeController.updateExchangeByStockExchange("BSE", exchangeDtls);
      assertEquals(200, response.getStatusCodeValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Test
  public void getCompanyListByStockExchange() {
    try {
      ResponseEntity<ResponseBean> response = exchangeController.getCompanyListByStockExchange("BSE");
      assertEquals(200, response.getStatusCodeValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Test
  public void handleException() {
    try {
      ResponseEntity<ResponseBean> response = exchangeController.handleException(new Exception("exception test"));
      assertEquals(500, response.getStatusCodeValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}