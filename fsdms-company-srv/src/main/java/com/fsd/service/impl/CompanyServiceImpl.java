/**
 * @author: Jon
 * @create: 2019-10-15 13:54
 **/
package com.fsd.service.impl;

import com.fsd.dao.CompanyDao;
import com.fsd.entity.Company;
import com.fsd.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

  @Autowired
  private CompanyDao companyDao;

  @Override
  public Company getCompanyById(Long id) {
    return companyDao.getOne(id);
  }

  @Override
  public Company getCompanyByCompanyName(String companyName) {
    return companyDao.findByCompanyName(companyName);
  }

  @Override
  public Company getCompanyByCompanyStockCode(String companyStockCode) {
    return companyDao.findByCompanyStockCode(companyStockCode);
  }

  @Override
  public List<Company> getAllBySectorName(String sectorName) {
    return companyDao.findAllBySectorName(sectorName);
  }

  @Override
  public List<Company> getAllByStockExchange(String stockExchange) {
    return companyDao.findAllByStockExchange(stockExchange);
  }

  @Override
  public List<Company> getAllByCompanyNameContaining(String companyName) {
    return companyDao.findAllByCompanyNameContaining(companyName);
  }

  @Override
  public List<Company> getAllByCompanyStockCodeContaining(String companyStockCode) {
    return companyDao.findAllByCompanyStockCodeContaining(companyStockCode);
  }

  @Override
  public List<Company> getAllCompany() {
    return companyDao.findAll();
  }

  @Override
  public Company insertUpdateCompany(Company tech) {
    return companyDao.saveAndFlush(tech);
  }

  @Override
  public int setActiveForCompany(String username, String active) {
    return companyDao.setCompanyActiveFor(username, active);
  }

}
