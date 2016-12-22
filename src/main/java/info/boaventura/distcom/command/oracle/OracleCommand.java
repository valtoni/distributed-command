package info.boaventura.distcom.command.oracle;

import java.sql.SQLException;
import java.util.Set;

public interface OracleCommand {

  void set(String param, String value);
  
  Set<String> params();

  void execute() throws SQLException;
  
  void setConnectionInstance(JdbcOracle jdbcOracle);
  
}
