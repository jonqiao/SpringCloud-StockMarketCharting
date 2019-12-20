/**
 * @author: Jon
 * @create: 2019-10-15 13:52
 **/
package com.fsd.service;

import com.fsd.entity.Company;

import java.util.List;

public interface CompanyService {

  Company getCompanyById(Long id);

  Company getCompanyByCompanyName(String companyName);

  Company getCompanyByCompanyStockCode(String companyStockCode);

  List<Company> getAllBySectorName(String sectorName);

  List<Company> getAllByStockExchange(String stockExchange);

  List<Company> getAllByCompanyNameContaining(String companyName);

  List<Company> getAllByCompanyStockCodeContaining(String companyStockCode);

  List<Company> getAllCompany();

  Company insertUpdateCompany(Company company);

  int setActiveForCompany(String username, String active);

}
