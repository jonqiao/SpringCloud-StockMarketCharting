/**
 * @author: Jon
 * @create: 2019-10-14 18:06
 **/
package com.fsd.dao;

import com.fsd.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsername(String username);

  //  Page<User> findByActive(String active, Pageable pageable);

  @Transactional
  @Modifying()
  @Query("update UserEntity u set u.active = ?2 where u.username = ?1")
  int setUserActiveFor(String username, String active);

  UserEntity findFirstByActive(String active);

}
