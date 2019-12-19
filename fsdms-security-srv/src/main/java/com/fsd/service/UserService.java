/**
 * @author: Jon
 * @create: 2019-10-14 15:58
 **/
package com.fsd.service;

import com.fsd.entity.UserEntity;

import java.util.List;

public interface UserService {

  UserEntity getUserByUsername(String username);

  UserEntity getUserById(Long id);

  List getAllUser();

  UserEntity saveUser(UserEntity user);

  int setActiveForUser(String username, String active);

  UserEntity getFirstByActive(String active);

}
