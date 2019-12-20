/**
 * @author: Jon
 * @create: 2019-10-13 18:02
 **/
package com.fsd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpoDtls implements Serializable {

  private static final long serialVersionUID = -1040531239876227147L;

  private String companyName;
  private String stockExchange;
  private BigDecimal pricePerShare;
  private Long totalNumberOfShares;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime openDateTime;
  private String remarks;

}
