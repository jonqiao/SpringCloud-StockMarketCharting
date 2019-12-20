/**
 * @author: Jon
 * @create: 2019-10-15 13:52
 **/
package com.fsd.service;

import com.fsd.entity.Sector;

import java.util.List;

public interface SectorService {

  Sector getSectorBySectorName(String sectorName);

  List<Sector> getAllSector();

  List<Sector> getAllBySectorNameContaining(String sectorName);

  Sector insertUpdateSector(Sector sector);

}
