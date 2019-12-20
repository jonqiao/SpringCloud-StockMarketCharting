/**
 * @author: Jon
 * @create: 2019-11-21 15:06
 **/
package com.fsd.dao;

import com.fsd.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockPriceDao extends JpaRepository<StockPrice, Long> {

  // used by StockPriceController
  StockPrice findFirstByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode);

  List<StockPrice> findAllByOrderByStockDateTimeDesc();

  List<StockPrice> findAllByCompanyStockCodeOrderByStockDateTimeDesc(String stockCode);

  List<StockPrice> findAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end);

  List<StockPrice> findAllByCompanyStockCodeAndStockDateTimeBetween(String stockCode, LocalDateTime start, LocalDateTime end);

  // used by UploadExcelController
  StockPrice findByCompanyStockCodeAndStockExchangeAndStockDateTime(String stockCode, String exchange, LocalDateTime ldt);

}
