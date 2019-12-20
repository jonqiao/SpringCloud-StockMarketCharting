/**
 * @author: Jon
 * @create: 2019-10-13 18:18
 **/
package com.fsd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDtls implements Serializable {

  private static final long serialVersionUID = -2989720358990366030L;

  //  private long id;
  private String stockExchange;
  private String brief;
  private String contactAddress;
  private String remarks;

}
