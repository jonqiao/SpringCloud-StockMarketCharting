/**
 * @author: Jon
 * @create: 2019-10-15 13:48
 **/
package com.fsd.controller;

import com.fsd.entity.StockPrice;
import com.fsd.model.StockPriceDtls;
import com.fsd.service.StockPriceService;
import com.fsd.utils.BeanUtilsCopy;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "StockPrice")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/stockprice")
public class StockPriceController {

  @Autowired
  private StockPriceService stockPriceService;

  @ApiOperation(value = "getFirstByCompanyStockCodeOrderByStockDateTimeDesc")
  @GetMapping("/current/{stockCode}")
  public ResponseEntity<ResponseBean> getFirstByCompanyStockCodeOrderByStockDateTimeDesc(@PathVariable("stockCode") String stockCode)
      throws Exception {
    StockPriceDtls stockPriceDtls = new StockPriceDtls();
    StockPrice stockPrice = stockPriceService.getFirstByCompanyStockCodeOrderByStockDateTimeDesc(stockCode);
    BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtls));
  }

  @ApiOperation(value = "getAllByOrderByStockDateTimeAsc")
  @GetMapping("/all")
  public ResponseEntity<ResponseBean> getAllByOrderByStockDateTimeAsc() throws Exception {
    List<StockPrice> stockPriceList = stockPriceService.getAllByOrderByStockDateTimeAsc();
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    for (StockPrice stockPrice : stockPriceList) {
      StockPriceDtls stockPriceDtls = new StockPriceDtls();
      BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
      stockPriceDtlsList.add(stockPriceDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
  }

  @ApiOperation(value = "getAllByCompanyStockCodeOrderByStockDateTimeAsc")
  @GetMapping("/all/{stockCode}")
  public ResponseEntity<ResponseBean> getAllByCompanyStockCodeOrderByStockDateTimeAsc(@PathVariable("stockCode") String stockCode) throws Exception {
    List<StockPrice> stockPriceList = stockPriceService.getAllByCompanyStockCodeOrderByStockDateTimeAsc(stockCode);
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    for (StockPrice stockPrice : stockPriceList) {
      StockPriceDtls stockPriceDtls = new StockPriceDtls();
      BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
      stockPriceDtlsList.add(stockPriceDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
  }

  @ApiOperation(value = "getAllByStockDateTimeBetween")
  @PostMapping("/allbetween")
  public ResponseEntity<ResponseBean> getAllByStockDateTimeBetween(@RequestBody Map params) throws Exception {
    LocalDateTime start = LocalDateTime.parse((CharSequence) params.get("start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime end = LocalDateTime.parse((CharSequence) params.get("end"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    List<StockPrice> stockPriceList = stockPriceService.getAllByStockDateTimeBetween(start, end);
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    for (StockPrice stockPrice : stockPriceList) {
      StockPriceDtls stockPriceDtls = new StockPriceDtls();
      BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
      stockPriceDtlsList.add(stockPriceDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
  }

  @ApiOperation(value = "getAllByCompanyStockCodeAndStockDateTimeBetween")
  @PostMapping("/allbetween/{stockCode}")
  public ResponseEntity<ResponseBean> getAllByCompanyStockCodeAndStockDateTimeBetween(@PathVariable("stockCode") String stockCode,
                                                                                      @RequestBody Map params) throws Exception {
    LocalDateTime start = LocalDateTime.parse((CharSequence) params.get("start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime end = LocalDateTime.parse((CharSequence) params.get("end"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    List<StockPrice> stockPriceList = stockPriceService.getAllByCompanyStockCodeAndStockDateTimeBetween(stockCode, start, end);
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    for (StockPrice stockPrice : stockPriceList) {
      StockPriceDtls stockPriceDtls = new StockPriceDtls();
      BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
      stockPriceDtlsList.add(stockPriceDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
  }

  @ApiOperation(value = "getAllByCompanyStockCodeInOrderByStockDateTimeAsc")
  @PostMapping("/all/stockCodeList")
  public List<StockPriceDtls> getAllByCompanyStockCodeInOrderByStockDateTimeAsc(@RequestBody Map params) throws Exception {
    List<String> stockCodeList = (List<String>) params.get("stockCodeList");
    List<StockPrice> stockPriceList = stockPriceService.getAllByCompanyStockCodeInOrderByStockDateTimeAsc(stockCodeList);
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    for (StockPrice stockPrice : stockPriceList) {
      StockPriceDtls stockPriceDtls = new StockPriceDtls();
      BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
      stockPriceDtlsList.add(stockPriceDtls);
    }
    //    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
    return stockPriceDtlsList;
  }

  @ApiOperation(value = "getAllByCompanyStockCodeInAndStockDateTimeBetween")
  @PostMapping("/allbetween/stockCodeList")
  public List<StockPriceDtls> getAllByCompanyStockCodeInAndStockDateTimeBetween(@RequestBody Map params) throws Exception {
    List<String> stockCodeList = (List<String>) params.get("stockCodeList");
    LocalDateTime start = LocalDateTime.parse((CharSequence) params.get("start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime end = LocalDateTime.parse((CharSequence) params.get("end"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    List<StockPrice> stockPriceList = stockPriceService.getAllByCompanyStockCodeInAndStockDateTimeBetween(stockCodeList, start, end);
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    for (StockPrice stockPrice : stockPriceList) {
      StockPriceDtls stockPriceDtls = new StockPriceDtls();
      BeanUtilsCopy.copyPropertiesNoNull(stockPrice, stockPriceDtls);
      stockPriceDtlsList.add(stockPriceDtls);
    }
    //    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
    return stockPriceDtlsList;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

}
