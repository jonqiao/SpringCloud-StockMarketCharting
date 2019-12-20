/**
 * @author: Jon
 * @create: 2019-11-08 00:10
 **/
package com.fsd.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class LowerTableNamingStrategy extends PhysicalNamingStrategyStandardImpl {

  private static final long serialVersionUID = -9146011318608495855L;

  @Override
  public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {

    String tableName = name.getText().toLowerCase();

    return Identifier.toIdentifier(tableName);
  }

}
