/**
 * @author: Jon
 * @create: 2019-10-15 13:52
 **/
package com.fsd.service;

import com.fsd.entity.Exchange;

import java.util.List;

public interface ExchangeService {

  Exchange getExchangeById(Long id);

  Exchange getExchangeByStockExchange(String stockExchange);

  List<Exchange> getAllExchange();

  Exchange insertUpdateExchange(Exchange exchange);

}
