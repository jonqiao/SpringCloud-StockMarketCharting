/**
 * @author: Jon
 * @create: 2019-10-15 13:48
 **/
package com.fsd.controller;

import com.fsd.entity.IpoDetails;
import com.fsd.model.IpoDtls;
import com.fsd.service.IpoDetailsService;
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

@Api(tags = "IpoDtls")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/ipodetails")
public class IpoDetailsController {

  @Autowired
  private IpoDetailsService ipoDetailsService;

  @ApiOperation(value = "getAllIpoDetails")
  @GetMapping("/all")
  public ResponseEntity<ResponseBean> getAllIpoDetails() throws Exception {
    List<IpoDetails> ipoList = ipoDetailsService.getAllOrderByOpenDateTimeAsc();
    List<IpoDtls> ipoDtlsList = new ArrayList<>();
    for (IpoDetails ipo : ipoList) {
      IpoDtls ipoDtls = new IpoDtls();
      BeanUtilsCopy.copyPropertiesNoNull(ipo, ipoDtls);
      ipoDtlsList.add(ipoDtls);
    }
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(ipoDtlsList));
  }

  @ApiOperation(value = "getIpoDetailsByCompanyName")
  @GetMapping("/{companyName}")
  public ResponseEntity<ResponseBean> getIpoDetailsByCompanyName(@PathVariable("companyName") String companyName) throws Exception {
    IpoDtls ipoDtls = new IpoDtls();
    IpoDetails ipo = ipoDetailsService.getIpoDetailsByCompanyName(companyName);
    BeanUtilsCopy.copyPropertiesNoNull(ipo, ipoDtls);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(ipoDtls));
  }

  @ApiOperation(value = "newIpoDetails")
  @PostMapping("/admin/new")
  public ResponseEntity<ResponseBean> newIpoDetails(@RequestBody IpoDtls ipoDtls) throws Exception {
    IpoDetails ipo = new IpoDetails();
    BeanUtilsCopy.copyPropertiesNoNull(ipoDtls, ipo);

    ipoDetailsService.insertUpdateIpoDetails(ipo);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("IPODETAILS NEW SUCCESSFULLY!"));
  }

  @ApiOperation(value = "updateIpoDetailsByCompanyName")
  @PostMapping("/admin/upt/{companyName}")
  public ResponseEntity<ResponseBean> updateIpoDetailsByCompanyName(@PathVariable("companyName") String companyName, @RequestBody IpoDtls ipoDtls)
      throws Exception {
    IpoDetails ipo = ipoDetailsService.getIpoDetailsByCompanyName(companyName);
    BeanUtilsCopy.copyPropertiesNoNull(ipoDtls, ipo);

    ipoDetailsService.insertUpdateIpoDetails(ipo);
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("IPODETAILS UPDATE SUCCESSFULLY!"));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

}
