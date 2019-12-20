/**
 * @author: Jon
 * @create: 2019-10-15 13:49
 **/
package com.fsd.dao;

import com.fsd.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyDao extends JpaRepository<Company, Long> {

  Company findByCompanyName(String companyName);

  Company findByCompanyStockCode(String companyStockCode);

  List<Company> findAllBySectorName(String sectorName);

  List<Company> findAllByStockExchange(String stockExchange);

  List<Company> findAllByCompanyNameContaining(String companyName);

  List<Company> findAllByCompanyStockCodeContaining(String companyStockCode);

  @Transactional
  @Modifying()
  @Query("update Company c set c.active = ?2 where c.companyName = ?1")
  int setCompanyActiveFor(String companyName, String active);

}
