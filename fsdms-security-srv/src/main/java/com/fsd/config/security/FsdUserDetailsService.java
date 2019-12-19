/**
 * @author: Jon
 * @create: 2019-10-25 17:48
 **/
package com.fsd.config.security;

import com.fsd.entity.UserEntity;
import com.fsd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FsdUserDetailsService implements UserDetailsService {

  @Autowired
  UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userService.getUserByUsername(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException("USERNAME NOT FOUND");
    }
    String password = userEntity.getPassword();
    String role = userEntity.getRole();
    Boolean userDisabled = false;
    if (!userEntity.getActive().equalsIgnoreCase("Y")) {
      userDisabled = true;
    }
    log.debug("UserDetailsService-passwordAfterEncoder = {}", password);
    log.debug("UserDetailsService-role = {}", role);
    log.debug("UserDetailsService-userDisabled = {}", userDisabled);
    return User.withUsername(username).password(password).disabled(userDisabled).roles(role).build();
    //    return User.withUsername(username).password(password).roles(role).build();
  }

}
