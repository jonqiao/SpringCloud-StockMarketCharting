/**
 * @author: Jon
 * @create: 2019-10-14 19:13
 **/
package com.fsd.service.impl;

import com.fsd.dao.UserDao;
import com.fsd.entity.UserEntity;
import com.fsd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  @Autowired
  private UserDao userDao;

  @Override
  public UserEntity getUserByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public UserEntity getUserById(Long id) {
    //    userDao.findById(id).get();
    return userDao.getOne(id);
  }

  @Override
  public List<UserEntity> getAllUser() {
    return userDao.findAll();
  }

  @Override
  public UserEntity saveUser(UserEntity user) {
    return userDao.saveAndFlush(user);
  }

  @Override
  public int setActiveForUser(String username, String active) {
    return userDao.setUserActiveFor(username, active);
  }

  @Override
  public UserEntity getFirstByActive(String active) {
    return userDao.findFirstByActive(active);
  }

}
