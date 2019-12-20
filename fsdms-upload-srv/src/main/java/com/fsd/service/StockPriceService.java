/**
 * @author: Jon
 * @create: 2019-11-21 15:41
 **/
package com.fsd.service;

import com.fsd.entity.StockPrice;

import java.time.LocalDateTime;
import java.util.List;

public interface StockPriceService {

  // used by StockPriceController
  StockPrice getFirstByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode);

  List<StockPrice> getAllByOrderByStockDateTimeDesc();

  List<StockPrice> getAllByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode);

  List<StockPrice> getAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end);

  List<StockPrice> getAllByCompanyStockCodeAndStockDateTimeBetween(String stockCode, LocalDateTime start, LocalDateTime end);

  // used by UploadExcelController
  StockPrice getByCompanyStockCodeAndStockExchangeAndStockDateTime(String stockCode, String exchange, LocalDateTime ldt);

  StockPrice insertUpdateStockPrice(StockPrice stockPrice);

  List<StockPrice> saveAllStockPrice(List<StockPrice> stockPriceList);

}
