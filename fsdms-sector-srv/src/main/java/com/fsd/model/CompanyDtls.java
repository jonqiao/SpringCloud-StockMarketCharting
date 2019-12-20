/**
 * @author: Jon
 * @create: 2019-10-13 18:18
 **/
package com.fsd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDtls implements Serializable {

  private static final long serialVersionUID = -2989720358990366030L;

  //  private long id;
  private String companyName;
  private BigDecimal turnover;
  private String ceo;
  private String boardOfDirectors;
  private String stockExchange;
  private String sectorName;
  private String briefWriteup;
  private String companyStockCode;
  private String active;

}
