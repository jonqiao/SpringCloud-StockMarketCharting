/**
 * @author: Jon
 * @create: 2019-11-21 15:41
 **/
package com.fsd.service;

import com.fsd.entity.StockPrice;

import java.time.LocalDateTime;
import java.util.List;

public interface StockPriceService {

  StockPrice getFirstByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode);

  List<StockPrice> getAllByOrderByStockDateTimeAsc();

  List<StockPrice> getAllByCompanyStockCodeOrderByStockDateTimeAsc(String stockCode);

  List<StockPrice> getAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end);

  List<StockPrice> getAllByCompanyStockCodeAndStockDateTimeBetween(String stockCode, LocalDateTime start, LocalDateTime end);

  List<StockPrice> getAllByCompanyStockCodeInOrderByStockDateTimeAsc(List<String> stockCodeList);

  List<StockPrice> getAllByCompanyStockCodeInAndStockDateTimeBetween(List<String> stockCodeList, LocalDateTime start, LocalDateTime end);

}
