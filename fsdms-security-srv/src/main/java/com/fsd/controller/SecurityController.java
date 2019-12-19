/**
 * @author: Jon
 * @create: 2019-10-16 14:08
 **/
package com.fsd.controller;

import com.fsd.model.AuthenticationRequest;
import com.fsd.model.AuthenticationResponse;
import com.fsd.model.UserDtls;
import com.fsd.utils.JwtTokenUtil;
import com.fsd.utils.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "Security")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/security", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityController {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @ApiOperation(value = "signin")
  @PostMapping("/signin")
  public ResponseEntity<ResponseBean> signin(@RequestBody AuthenticationRequest request) throws Exception {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Reload password post-security so we can generate token
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    String jwtToken = JwtTokenUtil.generateToken(userDetails, false);

    //    if (authentication != null) { // generateToken with authentication
    //      log.debug("SecurityController:authentication = {}", authentication.toString());
    //      jwtToken = JwtTokenUtil.generateToken(authentication, false);
    //    }

    // Return the token & UserDtls
    UserDtls userDtls = new UserDtls();
    userDtls.setUsername(userDetails.getUsername());
    Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
    log.debug("authorities = {}", authorities.toArray()[0].toString());
    userDtls.setRole(authorities.toArray()[0].toString());
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data(new AuthenticationResponse(jwtToken, userDtls)));
  }

  @ApiOperation(value = "admin authentication")
  @ApiResponses(value = { //
      @ApiResponse(code = 200, message = "Successfully retrieved list"), //
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"), //
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), //
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") //
  })
  //  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(value = "/admin")
  public ResponseEntity<ResponseBean> admin() throws Exception {
    log.debug("debug message ====> reach admin");
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("AUTHENTICATED - ADMIN VERIFIED"));
  }

  @ApiOperation(value = "users authentication")
  @GetMapping(value = "/authenticated")
  public ResponseEntity<ResponseBean> authenticated() throws Exception {
    log.debug("debug message ====> reach authenticated");
    return ResponseEntity.ok().body(new ResponseBean(OK.value(), OK.getReasonPhrase()).data("AUTHENTICATED - USER VERIFIED"));
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
