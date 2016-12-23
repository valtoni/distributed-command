package info.boaventura.distcom.command.action.oracle;

import java.util.Set;

public interface OracleCommand {

  void set(String param, String value);
  
  Set<String> params();

  void execute();
  
  void setConnectionInstance(JdbcOracle jdbcOracle);
  
}
