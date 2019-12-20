/**
 * @author: Jon
 * @create: 2019-10-15 13:49
 **/
package com.fsd.dao;

import com.fsd.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeDao extends JpaRepository<Exchange, Long> {

  Exchange findByStockExchange(String stockExchange);

}
