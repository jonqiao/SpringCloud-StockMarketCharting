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
  public List<StockPrice> getAllByOrderByStockDateTimeDesc() {
    return stockPriceDao.findAllByOrderByStockDateTimeDesc();
  }

  @Override
  public List<StockPrice> getAllByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode) {
    return stockPriceDao.findAllByCompanyStockCodeOrderByStockDateTimeDesc(stockCode);
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
  public StockPrice getByCompanyStockCodeAndStockExchangeAndStockDateTime(String stockCode, String exchange, LocalDateTime ldt) {
    return stockPriceDao.findByCompanyStockCodeAndStockExchangeAndStockDateTime(stockCode, exchange, ldt);
  }

  @Override
  public StockPrice insertUpdateStockPrice(StockPrice stockPrice) {
    return stockPriceDao.saveAndFlush(stockPrice);
  }

  @Override
  public List<StockPrice> saveAllStockPrice(List<StockPrice> stockPriceList) {
    return stockPriceDao.saveAll(stockPriceList);
  }

}
