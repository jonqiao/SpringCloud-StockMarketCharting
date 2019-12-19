/**
 * @author: Jon
 * @create: 2019-10-14 15:48
 **/
package com.fsd.controller;

import com.fsd.entity.UserEntity;
import com.fsd.exception.FsdAuthenticationException;
import com.fsd.model.UserDtls;
import com.fsd.service.UserService;
import com.fsd.utils.BeanUtilsCopy;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "User")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @ApiOperation(value = "SignUp", notes = "User SignUp from the UI")
  @PostMapping("/signup")
  public ResponseEntity<ResponseBean> UserSignUp(@RequestBody UserDtls userDtls) throws Exception {
    UserEntity user = new UserEntity();
    BeanUtilsCopy.copyPropertiesNoNull(userDtls, user);
    // encrypt password
    String password = user.getPassword();
    user.setPassword(passwordEncoder.encode(password));
    userService.saveUser(user);

    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("USER SIGNUP SUCCESSFULLY!"));
  }

  @ApiOperation(value = "Profile", notes = "update User profile from the UI")
  @PostMapping("/profile")
  public ResponseEntity<ResponseBean> updateUserProfile(@RequestBody UserDtls userDtls) throws Exception {
    // user can only update info of himself
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.debug("UserController-updateUserProfile = {}", authentication.getName());
    String currentUsername = authentication.getName();
    if (userDtls.getUsername().equalsIgnoreCase(currentUsername)) {
      UserEntity user = userService.getUserByUsername(userDtls.getUsername());
      BeanUtilsCopy.copyPropertiesNoNull(userDtls, user);

      // encrypt password
      if (!("".equalsIgnoreCase(userDtls.getPassword()) || userDtls.getPassword() == null)) {
        String password = userDtls.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);
      }

      return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("USER PROFILE UPDATE SUCCESSFULLY!"));
    } else {
      throw new FsdAuthenticationException("Username from token different from current Username!");
    }
  }

  //  @ApiOperation(value = "getUserById")
  //  @GetMapping("/id/{id}")
  //  public UserEntity getUserById(@PathVariable("id") Long id) {
  //    return userService.getUserById(id);
  //  }

  @ApiOperation(value = "getUserByUsername", notes = "for test only for now")
  @GetMapping("/{username}")
  public ResponseEntity<ResponseBean> getUserByUsername(@PathVariable("username") String username) throws Exception {
    // user can only get info of himself
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.debug("UserController-activeUserByUsername = {}", authentication.getName());
    String currentUsername = authentication.getName();
    if (username.equalsIgnoreCase(currentUsername)) {
      UserDtls userDtls = new UserDtls();
      UserEntity user = userService.getUserByUsername(username);
      BeanUtilsCopy.copyPropertiesNoNull(user, userDtls);

      return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(userDtls));
    } else {
      throw new FsdAuthenticationException("Username from token different from current Username!");
    }
  }

  @ApiOperation(value = "activeUser")
  @GetMapping("/active/{username}")
  public ResponseEntity<ResponseBean> activeUserByUsername(@PathVariable("username") String username) throws Exception {
    // user click this link from email received to active user
    userService.setActiveForUser(username, "Y");
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("User Active Confirmed!"));
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(FORBIDDEN)
  public ResponseEntity<ResponseBean> handleAuthentication403Exception(AuthenticationException exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(FORBIDDEN).body(new ResponseBean(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase()).error(exception.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseBean> handleException(Exception exception) throws Exception {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
        new ResponseBean(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase()).error(exception.getMessage()));
  }

}
