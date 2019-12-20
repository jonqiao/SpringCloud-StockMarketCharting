/**
 * @author: Jon
 * @create: 2019-10-29 03:11
 **/
package com.fsd.feigin;

import com.fsd.utils.ResponseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "fsdms-training-srv", url = "http://localhost:9052")
@FeignClient(name = "fsdms-company-srv")
public interface CompanyFeignClient {

  @RequestMapping(value = "/fsdms/api/v1/company/exchange/{stockExchange}", method = RequestMethod.GET)
  ResponseEntity<ResponseBean> getAllByStockExchange(@PathVariable("stockExchange") String stockExchange);

}
