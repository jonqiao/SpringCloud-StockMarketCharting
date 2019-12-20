/**
 * @author: Jon
 * @create: 2019-10-13 18:02
 **/
package com.fsd.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ipo_details")
public class IpoDetails extends AuditEntity implements Serializable {

  private static final long serialVersionUID = 427474823645243713L;

  @Column(name = "company_name")
  private String companyName;
  @Column(name = "stock_exchange")
  private String stockExchange;
  @Column(name = "price_per_share")
  private BigDecimal pricePerShare;
  @Column(name = "total_number_of_shares")
  private Long totalNumberOfShares;
  @Column(name = "open_date_time")
  //  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime openDateTime;
  private String remarks;

}
