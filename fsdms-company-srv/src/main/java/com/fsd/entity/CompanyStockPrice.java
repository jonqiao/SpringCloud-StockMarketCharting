///**
// * @author: Jon
// * @create: 2019-10-13 18:02
// **/
//package com.fsd.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
//@NoArgsConstructor
//@JsonIgnoreProperties("hibernateLazyInitializer")
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table(name = "company")
//public class CompanyStockPrice extends AuditEntity implements Serializable {
//
//  private static final long serialVersionUID = 3316204514924243455L;
//
//  @Column(name = "company_name")
//  private String companyName;
//  private BigDecimal turnover;
//  private String ceo;
//  @Column(name = "board_of_directors")
//  private String boardOfDirectors;
//  @Column(name = "stock_exchange")
//  private String stockExchange;
//  @Column(name = "sector_name")
//  private String sectorName;
//  @Column(name = "brief_writeup")
//  private String briefWriteup;
//  @Column(name = "company_stock_code")
//  private String companyStockCode;
//  private String active;
//
//  @OneToMany
//  List<StockPrice> stockPrice = new ArrayList<>();
//
//}
