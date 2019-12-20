/**
 * @author: Jon
 * @create: 2019-10-15 13:48
 **/
package com.fsd.controller;

import com.fsd.entity.Exchange;
import com.fsd.feigin.CompanyFeignClient;
import com.fsd.model.ExchangeDtls;
import com.fsd.service.ExchangeService;
import com.fsd.utils.BeanUtilsCopy;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Exchange")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/exchange")
public class ExchangeController {

  @Autowired
  private ExchangeService exchangeService;

  @Autowired
  private CompanyFeignClient companyFeignClient;

  public ExchangeController(ExchangeService exchangeService, CompanyFeignClient companyFeignClient) {
    this.exchangeService = exchangeService;
    this.companyFeignClient = companyFeignClient;
  }

  @ApiOperation(value = "getExchangeAll")
  @GetMapping("/admin/all")
  public ResponseEntity<ResponseBean> getExchangeAll() throws Exception {
    List<Exchange> exchangeList = exchangeService.getAllExchange();
    List<ExchangeDtls> exchangeDtlsList = new ArrayList<>();
    for (Exchange exchange : exchangeList) {
      ExchangeDtls exchangeDtls = new ExchangeDtls();
      BeanUtilsCopy.copyPropertiesNoNull(exchange, exchangeDtls);
      exchangeDtlsList.add(exchangeDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(exchangeDtlsList));
  }

  @ApiOperation(value = "getExchangeByStockExchange")
  @GetMapping("/admin/{stockExchange}")
  public ResponseEntity<ResponseBean> getExchangeByStockExchange(@PathVariable("stockExchange") String stockExchange) throws Exception {
    ExchangeDtls exchangeDtls = new ExchangeDtls();
    Exchange exchange = exchangeService.getExchangeByStockExchange(stockExchange);
    BeanUtilsCopy.copyPropertiesNoNull(exchange, exchangeDtls);

    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(exchangeDtls));
  }

  @ApiOperation(value = "newExchange")
  @PostMapping("/admin/new")
  public ResponseEntity<ResponseBean> newExchange(@RequestBody ExchangeDtls exchangeDtls) throws Exception {
    Exchange exchange = new Exchange();
    BeanUtilsCopy.copyPropertiesNoNull(exchangeDtls, exchange);

    exchangeService.insertUpdateExchange(exchange);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("STOCK-EXCHANGE NEW SUCCESSFULLY!"));
  }

  @ApiOperation(value = "updateExchangeByStockExchange")
  @PostMapping("/admin/upt/{stockExchange}")
  public ResponseEntity<ResponseBean> updateExchangeByStockExchange(@PathVariable("stockExchange") String stockExchange,
                                                                    @RequestBody ExchangeDtls exchangeDtls) throws Exception {
    Exchange exchange = exchangeService.getExchangeByStockExchange(stockExchange);
    BeanUtilsCopy.copyPropertiesNoNull(exchangeDtls, exchange);

    exchangeService.insertUpdateExchange(exchange);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("STOCK-EXCHANGE UPDATE SUCCESSFULLY!"));
  }

  @ApiOperation(value = "getCompanyListByStockExchange - FeignClient used to retrieve data from company service")
  @RequestMapping(value = "/admin/{stockExchange}/companylist", method = RequestMethod.GET)
  public ResponseEntity<ResponseBean> getCompanyListByStockExchange(@PathVariable("stockExchange") String stockExchange) {
    return companyFeignClient.getAllByStockExchange(stockExchange);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

}
