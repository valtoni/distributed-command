package info.boaventura.distcom.commands;

import java.sql.SQLException;
import java.util.Set;

public interface Command {

  void set(String param, String value);
  
  Set<String> params();

  void execute() throws SQLException;
  
  void setConnectionInstance(JdbcOracle jdbcOracle);
  
}
