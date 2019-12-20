/**
 * @author: Jon
 * @create: 2019-11-21 15:48
 **/
package com.fsd.service.impl;

import com.fsd.dao.StockPriceDao;
import com.fsd.entity.StockPrice;
import com.fsd.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockPriceServiceImpl implements StockPriceService {

  @Autowired
  private StockPriceDao stockPriceDao;

  @Override
  public StockPrice getFirstByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode) {
    return stockPriceDao.findFirstByCompanyStockCodeOrderByStockDateTimeDesc(stockCode);
  }

  @Override
  public List<StockPrice> getAllByOrderByStockDateTimeAsc() {
    return stockPriceDao.findAllByOrderByStockDateTimeAsc();
  }

  @Override
  public List<StockPrice> getAllByCompanyStockCodeOrderByStockDateTimeAsc(String stockCode) {
    return stockPriceDao.findAllByCompanyStockCodeOrderByStockDateTimeAsc(stockCode);
  }

  @Override
  public List<StockPrice> getAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end) {
    return stockPriceDao.findAllByStockDateTimeBetween(start, end);
  }

  @Override
  public List<StockPrice> getAllByCompanyStockCodeAndStockDateTimeBetween(String stockCode, LocalDateTime start, LocalDateTime end) {
    return stockPriceDao.findAllByCompanyStockCodeAndStockDateTimeBetween(stockCode, start, end);
  }

  @Override
  public List<StockPrice> getAllByCompanyStockCodeInOrderByStockDateTimeAsc(List<String> stockCodeList) {
    return stockPriceDao.findAllByCompanyStockCodeInOrderByStockDateTimeAsc(stockCodeList);
  }

  @Override
  public List<StockPrice> getAllByCompanyStockCodeInAndStockDateTimeBetween(List<String> stockCodeList, LocalDateTime start, LocalDateTime end) {
    return stockPriceDao.findAllByCompanyStockCodeInAndStockDateTimeBetween(stockCodeList, start, end);
  }

}
