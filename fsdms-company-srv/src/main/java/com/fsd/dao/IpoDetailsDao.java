/**
 * @author: Jon
 * @create: 2019-10-15 13:49
 **/
package com.fsd.dao;

import com.fsd.entity.IpoDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IpoDetailsDao extends JpaRepository<IpoDetails, Long> {

  IpoDetails findByCompanyName(String companyName);

  List<IpoDetails> findAllByOrderByOpenDateTimeAsc();

}
