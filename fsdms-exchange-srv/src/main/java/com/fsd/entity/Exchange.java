/**
 * @author: Jon
 * @create: 2019-10-13 18:02
 **/
package com.fsd.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "stock_exchange")
public class Exchange extends AuditEntity implements Serializable {

  private static final long serialVersionUID = -1504828568751917224L;

  @Column(name = "stock_exchange")
  private String stockExchange;
  private String brief;
  @Column(name = "contact_address")
  private String contactAddress;
  private String remarks;

}
