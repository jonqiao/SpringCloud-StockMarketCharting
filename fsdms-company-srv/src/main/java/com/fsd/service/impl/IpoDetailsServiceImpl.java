/**
 * @author: Jon
 * @create: 2019-11-21 14:28
 **/
package com.fsd.service.impl;

import com.fsd.dao.IpoDetailsDao;
import com.fsd.entity.IpoDetails;
import com.fsd.service.IpoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpoDetailsServiceImpl implements IpoDetailsService {

  @Autowired
  private IpoDetailsDao ipoDetailsDao;

  @Override
  public IpoDetails getIpoDetailsByCompanyName(String companyName) {
    return ipoDetailsDao.findByCompanyName(companyName);
  }

  @Override
  public List<IpoDetails> getAllOrderByOpenDateTimeAsc() {
    return ipoDetailsDao.findAllByOrderByOpenDateTimeAsc();
  }

  @Override
  public IpoDetails insertUpdateIpoDetails(IpoDetails ipoDetails) {
    return ipoDetailsDao.saveAndFlush(ipoDetails);
  }

}
