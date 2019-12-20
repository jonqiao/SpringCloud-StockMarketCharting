/**
 * @author: Jon
 * @create: 2019-10-15 13:48
 **/
package com.fsd.controller;

import com.fsd.entity.Company;
import com.fsd.model.CompanyDtls;
import com.fsd.service.CompanyService;
import com.fsd.utils.BeanUtilsCopy;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Company")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

  @Autowired
  private CompanyService companyService;

  @ApiOperation(value = "getAllCompany")
  @GetMapping("/all")
  public ResponseEntity<ResponseBean> getAllCompany() throws Exception {
    List<Company> companyList = companyService.getAllCompany();
    List<CompanyDtls> companyDtlsList = new ArrayList<>();
    for (Company company : companyList) {
      CompanyDtls companyDtls = new CompanyDtls();
      BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
      companyDtlsList.add(companyDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtlsList));
  }

  @ApiOperation(value = "getCompanyByCompanyName")
  @GetMapping("/name/{companyName}")
  public ResponseEntity<ResponseBean> getCompanyByCompanyName(@PathVariable("companyName") String companyName) throws Exception {
    CompanyDtls companyDtls = new CompanyDtls();
    Company company = companyService.getCompanyByCompanyName(companyName);
    BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtls));
  }

  @ApiOperation(value = "getCompanyByCompanyStockCode")
  @GetMapping("/code/{companyStockCode}")
  public ResponseEntity<ResponseBean> getCompanyByCompanyStockCode(@PathVariable("companyStockCode") String companyStockCode) throws Exception {
    CompanyDtls companyDtls = new CompanyDtls();
    Company company = companyService.getCompanyByCompanyStockCode(companyStockCode);
    BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtls));
  }

  @ApiOperation(value = "getAllBySectorName")
  @GetMapping("/sector/{sectorName}")
  public List<CompanyDtls> getAllBySectorName(@PathVariable("sectorName") String sectorName) throws Exception {
    List<Company> companyList = companyService.getAllBySectorName(sectorName);
    List<CompanyDtls> companyDtlsList = new ArrayList<>();
    for (Company company : companyList) {
      CompanyDtls companyDtls = new CompanyDtls();
      BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
      companyDtlsList.add(companyDtls);
    }
    //    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtlsList));
    return companyDtlsList;
  }

  @ApiOperation(value = "getAllByStockExchange")
  @GetMapping("/exchange/{stockExchange}")
  public ResponseEntity<ResponseBean> getAllByStockExchange(@PathVariable("stockExchange") String stockExchange) throws Exception {
    List<Company> companyList = companyService.getAllByStockExchange(stockExchange);
    List<CompanyDtls> companyDtlsList = new ArrayList<>();
    for (Company company : companyList) {
      CompanyDtls companyDtls = new CompanyDtls();
      BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
      companyDtlsList.add(companyDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtlsList));
  }

  @ApiOperation(value = "newCompany")
  @PostMapping("/admin/new")
  public ResponseEntity<ResponseBean> newCompany(@RequestBody CompanyDtls companyDtls) throws Exception {
    Company company = new Company();
    BeanUtilsCopy.copyPropertiesNoNull(companyDtls, company);

    companyService.insertUpdateCompany(company);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("COMPANY NEW SUCCESSFULLY!"));
  }

  @ApiOperation(value = "updateCompanyByCompanyName")
  @PostMapping("/admin/upt/{companyName}")
  public ResponseEntity<ResponseBean> updateCompanyByCompanyName(@PathVariable("companyName") String companyName,
                                                                 @RequestBody CompanyDtls companyDtls) throws Exception {
    Company company = companyService.getCompanyByCompanyName(companyName);
    BeanUtilsCopy.copyPropertiesNoNull(companyDtls, company);

    companyService.insertUpdateCompany(company);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("COMPANY UPDATE SUCCESSFULLY!"));
  }

  @ApiOperation(value = "activeCompany")
  @PostMapping("/admin/active/{companyName}")
  public ResponseEntity<ResponseBean> activeCompany(@PathVariable("companyName") String companyName) throws Exception {
    companyService.setActiveForCompany(companyName, "Y");
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("Company Active SUCCESSFULLY!"));
  }

  @ApiOperation(value = "deactiveCompany")
  @PostMapping("/admin/deactive/{companyName}")
  public ResponseEntity<ResponseBean> deactiveCompany(@PathVariable("companyName") String companyName) throws Exception {
    companyService.setActiveForCompany(companyName, "N");
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("Company Deactive SUCCESSFULLY!"));
  }

  @ApiOperation(value = "DebounceRequestsByCompanyName")
  @GetMapping("/nameDebounce/{companyName}")
  public ResponseEntity<ResponseBean> DebounceRequestsByCompanyName(@PathVariable("companyName") String companyName) throws Exception {
    List<Company> companyList = companyService.getAllByCompanyNameContaining(companyName);
    List<CompanyDtls> companyDtlsList = new ArrayList<>();
    for (Company company : companyList) {
      CompanyDtls companyDtls = new CompanyDtls();
      BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
      companyDtlsList.add(companyDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtlsList));
  }

  @ApiOperation(value = "DebounceRequestsByCompanyStockCode")
  @GetMapping("/codeDebounce/{companyStockCode}")
  public ResponseEntity<ResponseBean> DebounceRequestsByCompanyStockCode(@PathVariable("companyStockCode") String companyStockCode) throws Exception {
    List<Company> companyList = companyService.getAllByCompanyStockCodeContaining(companyStockCode);
    List<CompanyDtls> companyDtlsList = new ArrayList<>();
    for (Company company : companyList) {
      CompanyDtls companyDtls = new CompanyDtls();
      BeanUtilsCopy.copyPropertiesNoNull(company, companyDtls);
      companyDtlsList.add(companyDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(companyDtlsList));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

}
