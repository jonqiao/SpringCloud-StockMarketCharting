/**
 * @author: Jon
 * @create: 2019-10-15 13:54
 **/
package com.fsd.service.impl;

import com.fsd.dao.ExchangeDao;
import com.fsd.entity.Exchange;
import com.fsd.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

  @Autowired
  private ExchangeDao exchangeDao;

  @Override
  public Exchange getExchangeById(Long id) {
    return exchangeDao.getOne(id);
  }

  @Override
  public Exchange getExchangeByStockExchange(String stockExchange) {
    return exchangeDao.findByStockExchange(stockExchange);
  }

  @Override
  public List<Exchange> getAllExchange() {
    return exchangeDao.findAll();
  }

  @Override
  public Exchange insertUpdateExchange(Exchange exchange) {
    return exchangeDao.saveAndFlush(exchange);
  }

}
