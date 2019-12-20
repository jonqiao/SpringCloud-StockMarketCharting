/**
 * @author: Jon
 * @create: 2019-10-15 13:49
 **/
package com.fsd.dao;

import com.fsd.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorDao extends JpaRepository<Sector, Long> {

  Sector findBySectorName(String sectorName);

  List<Sector> findAllBySectorNameContaining(String sectorName);

}
