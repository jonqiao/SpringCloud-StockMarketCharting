/**
 * @author: Jon
 * @create: 2019-11-22 17:08
 **/
package com.fsd.controller;

import com.fsd.entity.StockPrice;
import com.fsd.model.StockPriceDtls;
import com.fsd.service.StockPriceService;
import com.fsd.utils.BeanUtilsCopy;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "UploadExcel")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/upload")
public class UploadExcelController {

  @Autowired
  private StockPriceService stockPriceService;

  @ApiOperation(value = "uploadImportExcel")
  @PostMapping(value = "/admin/importexcel")
  public ResponseEntity<ResponseBean> uploadImportExcel(@RequestParam("excelfile") MultipartFile file, HttpServletRequest request,
                                                        HttpServletResponse response) throws Exception {
    StringBuilder buffer = new StringBuilder("uploadImportExcel start...\n");
    Workbook workbook = null;
    try {
      String fileName = file.getOriginalFilename();
      InputStream inputStream = file.getInputStream();

      if (fileName.matches("^.+\\.(?i)(xls)$")) { // EXCEL2003
        workbook = new HSSFWorkbook(inputStream);
      }
      if (fileName.matches("^.+\\.(?i)(xlsx)$")) { // EXCEL2007
        workbook = new XSSFWorkbook(inputStream);
      }

      if (workbook != null) {
        // int sheetsNum = workbook.getNumberOfSheets();
        Sheet sheet = workbook.getSheetAt(0);
        int allRowNum = sheet.getLastRowNum();
        log.debug("allRowNum = {}", allRowNum);
        if (allRowNum == 0) {
          buffer.append("EXCELFILE is Blank!\n");
          log.debug("EXCELFILE is Blank!\n");
        }

        log.debug("parse&save excel start = {}", LocalDateTime.now());
        List<StockPriceDtls> stockPriceDtlsList = new ArrayList<>();
        List<StockPrice> stockPriceList = new ArrayList<>();
        for (int i = 1; i <= allRowNum; i++) {
          StockPriceDtls stockPriceDtls = new StockPriceDtls();
          Row row = sheet.getRow(i);
          if (row != null) {
            Cell cell1 = row.getCell(0); // first column - String Company Code
            Cell cell2 = row.getCell(1); // second column - String stockExchange
            Cell cell3 = row.getCell(2); // third column - BigDecimal currentPrice
            Cell cell4 = row.getCell(3); // forth column - LocalDateTime stockDate
            Cell cell5 = row.getCell(4); // fifth column - LocalDateTime stockTime

            if (cell1 == null || cell2 == null || cell3 == null || cell4 == null || cell5 == null) {
              buffer.append("Row " + i + " has null data!\n");
              log.debug("Row " + i + " has null data!\n");
              break;
            }

            // The original excel have special space with Ascii: 160, but normal space with Ascii: 32, so need customer process here
            stockPriceDtls.setCompanyStockCode(cell1.getStringCellValue().trim().replaceAll("\\u00A0", ""));
            stockPriceDtls.setStockExchange(cell2.getStringCellValue().trim());
            stockPriceDtls.setCurrentPrice(new BigDecimal(cell3.getNumericCellValue()));

            String cell4Str = cell4.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
            String cell5Str = cell5.getStringCellValue();
            String cell45Str = cell4Str.trim() + " " + cell5Str.trim();
            log.debug("cell4Str = {}, cell5Str = {}, cell45Str = {}", cell4Str, cell5Str, cell45Str);
            LocalDateTime stockDateTime = LocalDateTime.parse((CharSequence) cell45Str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            stockPriceDtls.setStockDateTime(stockDateTime);

            stockPriceDtlsList.add(stockPriceDtls);
            StockPrice stockPrice = stockPriceService
                .getByCompanyStockCodeAndStockExchangeAndStockDateTime(stockPriceDtls.getCompanyStockCode(), stockPriceDtls.getStockExchange(),
                                                                       stockPriceDtls.getStockDateTime());
            if (stockPrice == null) {
              stockPrice = new StockPrice();
            }
            BeanUtilsCopy.copyPropertiesNoNull(stockPriceDtls, stockPrice);
            //            stockPriceService.insertUpdateStockPrice(stockPrice);
            stockPriceList.add(stockPrice);
          }
        }
        // saveall for batch process to avoid performance issue, but may still have issues when there are large number records
        if (stockPriceList.size() > 0) {
          stockPriceService.saveAllStockPrice(stockPriceList);
        }
        log.debug("parse&save excel end = {}", LocalDateTime.now());

        if (stockPriceDtlsList.size() > 0) {
          // solution 1:
          //        Collections.sort(stockPriceDtlsList, new Comparator<StockPriceDtls>() {
          //          @Override
          //          public int compare(StockPriceDtls o1, StockPriceDtls o2) {
          //            return o1.getStockDateTime().compareTo(o2.getStockDateTime());
          //          }
          //        });
          // solution 2: JAVA8 - use list sort method directly
          //        stockPriceDtlsList.sort(new Comparator<StockPriceDtls>() {
          //          @Override
          //          public int compare(StockPriceDtls o1, StockPriceDtls o2) {
          //            return o1.getStockDateTime().compareTo(o2.getStockDateTime());
          //          }
          //        });
          // solution 3: JAVA8 - Lambda
          //        stockPriceDtlsList.sort(Comparator.comparing(StockPriceDtls::getStockDateTime));
          // solution 4: JAVA8 - Lambda
          //        Comparator<StockPriceDtls> comparator = (o1, o2) -> o1.getStockDateTime().compareTo(o2.getStockDateTime());
          //        stockPriceDtlsList.sort(comparator);
          // solution 5: JAVA8 - Lambda
          //        stockPriceDtlsList.sort(((o1, o2) -> o1.getStockDateTime().compareTo(o2.getStockDateTime())));

          // 1. groupby CompanyStockCode 2. Iterate map to consolidate info 3. return consolidate result to frontend
          List consolidateList = new ArrayList<>();
          Map<String, List<StockPriceDtls>> groupMap = stockPriceDtlsList.stream()
                                                                         .collect(Collectors.groupingBy(StockPriceDtls::getCompanyStockCode));
          for (Map.Entry<String, List<StockPriceDtls>> entry : groupMap.entrySet()) {
            // sort first
            entry.getValue().sort(((o1, o2) -> o1.getStockDateTime().compareTo(o2.getStockDateTime())));

            Map consolidate = new HashMap();
            consolidate.put("companyStockCode", entry.getKey());
            consolidate.put("stockExchange", entry.getValue().get(0).getStockExchange());
            consolidate.put("noOfRecord", entry.getValue().size());
            //            consolidate.put("fromDateTime", entry.getValue().get(0).getStockDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd
            //            HH:mm:ss")));
            //            consolidate.put("toDateTime", entry.getValue().get(entry.getValue().size() - 1).getStockDateTime()
            //                                               .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            // can also do the format through angular-frontend directly
            consolidate.put("fromDateTime", entry.getValue().get(0).getStockDateTime().toString());
            consolidate.put("toDateTime", entry.getValue().get(entry.getValue().size() - 1).getStockDateTime().toString());
            consolidateList.add(consolidate);
          }

          return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(consolidateList));
        }
      } else {
        buffer.append("File Format is Invalid!\n");
        log.debug("File Format is Invalid!\n");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      buffer.append(e.getMessage());
    } finally {
      if (workbook != null) {
        workbook.close();
      }
    }
    buffer.append("uploadImportExcel end.");
    return ResponseEntity.ok().body(new ResponseBean(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase()).error(buffer));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

  // Local Test w/ and w/o batch
  //  w/o batch insert:
  //      2019-11-24 13:13:12,427 DEBUG (UploadExcelController.java:70)- allRowNum = 72
  //      2019-11-24 13:13:12,432 DEBUG (UploadExcelController.java:76)- parse&save excel start = 2019-11-24T13:13:12.432
  //      2019-11-24 13:13:14,129 DEBUG (UploadExcelController.java:124)- parse&save excel end = 2019-11-24T13:13:14.129
  //  w/o batch update:
  //      2019-11-24 13:13:58,069 DEBUG (UploadExcelController.java:70)- allRowNum = 72
  //      2019-11-24 13:13:58,076 DEBUG (UploadExcelController.java:76)- parse&save excel start = 2019-11-24T13:13:58.076
  //      2019-11-24 13:13:59,173 DEBUG (UploadExcelController.java:124)- parse&save excel end = 2019-11-24T13:13:59.173
  //
  //  w/ batch insert:
  //      2019-11-24 13:09:49,983 DEBUG (UploadExcelController.java:70)- allRowNum = 72
  //      2019-11-24 13:09:49,989 DEBUG (UploadExcelController.java:76)- parse&save excel start = 2019-11-24T13:09:49.989
  //      2019-11-24 13:09:51,179 DEBUG (UploadExcelController.java:124)- parse&save excel end = 2019-11-24T13:09:51.179
  //  w/ batch update:
  //      2019-11-24 13:11:08,273 DEBUG (UploadExcelController.java:70)- allRowNum = 72
  //      2019-11-24 13:11:08,307 DEBUG (UploadExcelController.java:76)- parse&save excel start = 2019-11-24T13:11:08.307
  //      2019-11-24 13:11:08,944 DEBUG (UploadExcelController.java:124)- parse&save excel end = 2019-11-24T13:11:08.944

}
