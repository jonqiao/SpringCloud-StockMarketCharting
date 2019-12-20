/**
 * @author: Jon
 * @create: 2019-10-29 03:11
 **/
package com.fsd.feigin;

import com.fsd.model.CompanyDtls;
import com.fsd.model.StockPriceDtls;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

//@FeignClient(name = "fsdms-training-srv", url = "http://localhost:9052")
@FeignClient(name = "fsdms-company-srv")
public interface CompanyFeignClient {

  @RequestMapping(value = "/fsdms/api/v1/company/sector/{sectorName}", method = RequestMethod.GET)
  List<CompanyDtls> getAllBySectorName(@PathVariable("sectorName") String sectorName);

  @RequestMapping(value = "/fsdms/api/v1/stockprice/all/stockCodeList", method = RequestMethod.POST)
  List<StockPriceDtls> getAllByCompanyStockCodeInOrderByStockDateTimeAsc(@RequestBody Map params);

  @RequestMapping(value = "/fsdms/api/v1/stockprice/allbetween/stockCodeList", method = RequestMethod.POST)
  List<StockPriceDtls> getAllByCompanyStockCodeInAndStockDateTimeBetween(@RequestBody Map params);

}
