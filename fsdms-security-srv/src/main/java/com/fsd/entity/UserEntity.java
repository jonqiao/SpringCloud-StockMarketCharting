/**
 * @author: Jon
 * @create: 2019-10-13 18:05
 **/
package com.fsd.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class UserEntity extends AuditEntity implements Serializable {

  private static final long serialVersionUID = 8077196416587889582L;

  private String username;
  private String password;
  private String role;
  private String email;
  private String mobile;
  private String active;

}
