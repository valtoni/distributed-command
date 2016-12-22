package info.boaventura.distcom.commands;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class SingleCommandAbstract {

  String commandTemplate;
  
  JdbcOracle jdbcOracle;
  
  Map<String, String> parameters;
  
  public void execute() throws SQLException {
    Collection<String> values = parameters.values();
    String[] valuesArray = values.toArray(new String[]{});
    String command = String.format(commandTemplate, (Object[])valuesArray);
    jdbcOracle.execStatement(command);
  }

  public void setConnectionInstance(JdbcOracle jdbcOracle) {
    this.jdbcOracle = jdbcOracle;
  }
  
  public void set(String name, String value) {
    if (!parameters.containsKey(name)) {
      throw new RuntimeException("Command " + getClass().getSimpleName() + " doesn't have param name " + name);
    }
    parameters.put(name, value);
  }
  
  public Set<String> params() {
    return parameters.keySet();
  }

  
}
