/**
 * @author: Jon
 * @create: 2019-10-13 18:36
 **/
package com.fsd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtls implements Serializable {

  private static final long serialVersionUID = 6962865549338523526L;

  //  private long id;
  private String username;
  private String password;
  private String role;
  private String email;
  private String mobile;
  private String active;

}
