/**
 * @author: Jon
 * @create: 2019-10-15 13:54
 **/
package com.fsd.service.impl;

import com.fsd.dao.SectorDao;
import com.fsd.entity.Sector;
import com.fsd.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {

  @Autowired
  private SectorDao sectorDao;

  @Override
  public Sector getSectorBySectorName(String sectorName) {
    return sectorDao.findBySectorName(sectorName);
  }

  @Override
  public List<Sector> getAllSector() {
    return sectorDao.findAll();
  }

  @Override
  public List<Sector> getAllBySectorNameContaining(String sectorName) {
    return sectorDao.findAllBySectorNameContaining(sectorName);
  }

  @Override
  public Sector insertUpdateSector(Sector sector) {
    return sectorDao.saveAndFlush(sector);
  }

}
