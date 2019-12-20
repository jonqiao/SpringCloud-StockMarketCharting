/**
 * @author: Jon
 * @create: 2019-10-15 13:48
 **/
package com.fsd.controller;

import com.fsd.entity.Sector;
import com.fsd.feigin.CompanyFeignClient;
import com.fsd.model.CompanyDtls;
import com.fsd.model.SectorDtls;
import com.fsd.model.StockPriceDtls;
import com.fsd.service.SectorService;
import com.fsd.utils.BeanUtilsCopy;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Exchange")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/sector")
public class SectorController {

  @Autowired
  private SectorService sectorService;

  @Autowired
  private CompanyFeignClient companyFeignClient;

  @ApiOperation(value = "getSectorAll")
  @GetMapping("/all")
  public ResponseEntity<ResponseBean> getSectorAll() throws Exception {
    List<Sector> sectorList = sectorService.getAllSector();
    List<SectorDtls> sectorDtlsList = new ArrayList<>();
    for (Sector sector : sectorList) {
      SectorDtls sectorDtls = new SectorDtls();
      BeanUtilsCopy.copyPropertiesNoNull(sector, sectorDtls);
      sectorDtlsList.add(sectorDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(sectorDtlsList));
  }

  @ApiOperation(value = "getSectorBySectorName")
  @GetMapping("/{sectorName}")
  public ResponseEntity<ResponseBean> getSectorBySectorName(@PathVariable("sectorName") String sectorName) throws Exception {
    SectorDtls sectorDtls = new SectorDtls();
    Sector sector = sectorService.getSectorBySectorName(sectorName);
    BeanUtilsCopy.copyPropertiesNoNull(sector, sectorDtls);

    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(sectorDtls));
  }

  @ApiOperation(value = "newSector")
  @PostMapping("/admin/new")
  public ResponseEntity<ResponseBean> newSector(@RequestBody SectorDtls sectorDtls) throws Exception {
    Sector sector = new Sector();
    BeanUtilsCopy.copyPropertiesNoNull(sectorDtls, sector);

    sectorService.insertUpdateSector(sector);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("SECTOR NEW SUCCESSFULLY!"));
  }

  @ApiOperation(value = "updateSectorBySectorName")
  @PostMapping("/admin/upt/{sectorName}")
  public ResponseEntity<ResponseBean> updateSectorBySectorName(@PathVariable("sectorName") String sectorName, @RequestBody SectorDtls sectorDtls)
      throws Exception {
    Sector sector = sectorService.getSectorBySectorName(sectorName);
    BeanUtilsCopy.copyPropertiesNoNull(sectorDtls, sector);

    sectorService.insertUpdateSector(sector);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("SECTOR UPDATE SUCCESSFULLY!"));
  }

  @ApiOperation(value = "DebounceRequestsBySectorName")
  @GetMapping("/nameDebounce/{sectorName}")
  public ResponseEntity<ResponseBean> DebounceRequestsBySectorName(@PathVariable("sectorName") String sectorName) throws Exception {
    List<Sector> sectorList = sectorService.getAllBySectorNameContaining(sectorName);
    List<SectorDtls> sectorDtlsList = new ArrayList<>();
    for (Sector sector : sectorList) {
      SectorDtls sectorDtls = new SectorDtls();
      BeanUtilsCopy.copyPropertiesNoNull(sector, sectorDtls);
      sectorDtlsList.add(sectorDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(sectorDtlsList));
  }

  @ApiOperation(value = "getCompanyListBySectorName - FeignClient used to retrieve data from company service")
  @RequestMapping(value = "/{sectorName}/companylist", method = RequestMethod.GET)
  public ResponseEntity<ResponseBean> getCompanyListBySectorName(@PathVariable("sectorName") String sectorName) throws Exception {
    List<CompanyDtls> companyDtlsList = companyFeignClient.getAllBySectorName(sectorName);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtlsList));
  }

  @ApiOperation(value = "getStockPriceByCompanyStockCodeInOrderByStockDateTimeAsc - FeignClient used to retrieve data from company service")
  @RequestMapping(value = "/stockprice/allcode", method = RequestMethod.POST)
  public ResponseEntity<ResponseBean> getStockPriceByCompanyStockCodeInOrderByStockDateTimeAsc(@RequestBody Map params) throws Exception {
    List<StockPriceDtls> stockPriceDtlsList = companyFeignClient.getAllByCompanyStockCodeInOrderByStockDateTimeAsc(params);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
  }

  @ApiOperation(value = "getStockPriceByCompanyStockCodeInAndStockDateTimeBetween - FeignClient used to retrieve data from company service")
  @RequestMapping(value = "/stockprice/allcodebetween", method = RequestMethod.POST)
  public ResponseEntity<ResponseBean> getStockPriceByCompanyStockCodeInAndStockDateTimeBetween(@RequestBody Map params) throws Exception {
    List<StockPriceDtls> stockPriceDtlsList = companyFeignClient.getAllByCompanyStockCodeInAndStockDateTimeBetween(params);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(stockPriceDtlsList));
  }

  @ApiOperation(value = "getSectorPriceBySectorName - FeignClient used to retrieve data from company service")
  @RequestMapping(value = "/{sectorName}/sectorprice", method = RequestMethod.GET)
  public ResponseEntity<ResponseBean> getSectorPriceBySectorName(@PathVariable("sectorName") String sectorName) throws Exception {
    Sector sector = sectorService.getSectorBySectorName(sectorName);
    List<CompanyDtls> companyDtlsList = companyFeignClient.getAllBySectorName(sectorName);
    List<String> stockCodeList = companyDtlsList.stream().map(CompanyDtls::getCompanyStockCode).collect(Collectors.toList());
    Map params = new HashMap(1);
    params.put("stockCodeList", stockCodeList);
    List<StockPriceDtls> stockPriceDtlsList = (List<StockPriceDtls>) companyFeignClient.getAllByCompanyStockCodeInOrderByStockDateTimeAsc(params);

    Map<LocalDateTime, List<StockPriceDtls>> groupMap = stockPriceDtlsList.stream().collect(Collectors.groupingBy(StockPriceDtls::getStockDateTime));
    List<SectorDtls> sectorDtlsList = new ArrayList<>();
    for (Map.Entry<LocalDateTime, List<StockPriceDtls>> entry : groupMap.entrySet()) {
      // for specific sector, calculate the SectorPrice(avg) group by SectorDateTime
      SectorDtls sectorDtls = new SectorDtls();
      BeanUtilsCopy.copyPropertiesNoNull(sector, sectorDtls);
      // calculate: sum, avg, group
      // solution 1:
      //      double sectorPrice = stockPriceDtlsList.stream().map(StockPriceDtls::getCurrentPrice).mapToDouble(BigDecimal::doubleValue).average()
      //                                             .getAsDouble();
      // solution 2:
      BigDecimal sectorPrice = entry.getValue().stream().map(StockPriceDtls::getCurrentPrice).reduce(BigDecimal.ZERO, BigDecimal::add)
                                    .divide(new BigDecimal(entry.getValue().size()), 2, ROUND_HALF_DOWN);
      sectorDtls.setSectorDateTime(entry.getKey());
      sectorDtls.setSectorPrice(sectorPrice);
      sectorDtlsList.add(sectorDtls);
    }
    sectorDtlsList.sort(((o1, o2) -> o1.getSectorDateTime().compareTo(o2.getSectorDateTime())));
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(sectorDtlsList));
  }

  @ApiOperation(value = "getSectorPriceBySectorNameAndStockDateTimeBetween - FeignClient used to retrieve data from company service")
  @RequestMapping(value = "/{sectorName}/sectorpricebetween", method = RequestMethod.POST)
  public ResponseEntity<ResponseBean> getSectorPriceBySectorNameAndStockDateTimeBetween(@PathVariable("sectorName") String sectorName,
                                                                                        @RequestBody Map params) throws Exception {
    Sector sector = sectorService.getSectorBySectorName(sectorName);
    List<CompanyDtls> companyDtlsList = companyFeignClient.getAllBySectorName(sectorName);
    List<String> stockCodeList = companyDtlsList.stream().map(CompanyDtls::getCompanyStockCode).collect(Collectors.toList());
    params.put("stockCodeList", stockCodeList);
    List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
    if (params.size() == 1) {
      stockPriceDtlsList = (List<StockPriceDtls>) companyFeignClient.getAllByCompanyStockCodeInOrderByStockDateTimeAsc(params);
    } else if (params.size() > 1) {
      stockPriceDtlsList = (List<StockPriceDtls>) companyFeignClient.getAllByCompanyStockCodeInAndStockDateTimeBetween(params);
    }

    Map<LocalDateTime, List<StockPriceDtls>> groupMap = stockPriceDtlsList.stream().collect(Collectors.groupingBy(StockPriceDtls::getStockDateTime));
    List<SectorDtls> sectorDtlsList = new ArrayList<>();
    for (Map.Entry<LocalDateTime, List<StockPriceDtls>> entry : groupMap.entrySet()) {
      // for specific sector, calculate the SectorPrice(avg) group by SectorDateTime
      SectorDtls sectorDtls = new SectorDtls();
      BeanUtilsCopy.copyPropertiesNoNull(sector, sectorDtls);
      // calculate: sum, avg, group
      // solution 1:
      //      double sectorPrice = stockPriceDtlsList.stream().map(StockPriceDtls::getCurrentPrice).mapToDouble(BigDecimal::doubleValue).average()
      //                                             .getAsDouble();
      // solution 2:
      BigDecimal sectorPrice = entry.getValue().stream().map(StockPriceDtls::getCurrentPrice).reduce(BigDecimal.ZERO, BigDecimal::add)
                                    .divide(new BigDecimal(entry.getValue().size()), 2, ROUND_HALF_DOWN);
      sectorDtls.setSectorDateTime(entry.getKey());
      sectorDtls.setSectorPrice(sectorPrice);
      sectorDtlsList.add(sectorDtls);
    }
    sectorDtlsList.sort(((o1, o2) -> o1.getSectorDateTime().compareTo(o2.getSectorDateTime())));
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(sectorDtlsList));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

}
