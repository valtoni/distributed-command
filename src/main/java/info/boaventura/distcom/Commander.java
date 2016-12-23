package info.boaventura.distcom;

import java.sql.SQLException;
import java.util.Collection;

import info.boaventura.distcom.command.action.oracle.JdbcOracle;
import info.boaventura.distcom.command.action.oracle.OracleCommand;
import info.boaventura.distcom.oracle.OracleConnection;
import info.boaventura.distcom.oracle.OrafileParser;

public class Commander {

  Collection<OracleConnection> connections;
  
  private String orafile;
  
  private String user;
  
  private String password;
  
  public Commander(String orafile, String user, String password) {
    this.orafile = orafile;
    this.user = user;
    this.password = password;
    setupConnections();
  }
  
  private void setupConnections() {
    this.connections = new OrafileParser(this.orafile).getConnections().values();
  }
  
  
  public void execute(OracleCommand command) {
    JdbcOracle jdbcOracle;

    for (OracleConnection oracleConnection: connections) {
      for (String jdbcUrl: oracleConnection.getJdbcUrls()) {
        try {
          jdbcOracle = new JdbcOracle(jdbcUrl);
        } catch (SQLException e) {
          throw new RuntimeException("Cannot found Oracle driver");
        }
        try {
          jdbcOracle.openConnection(user, password);
          command.setConnectionInstance(jdbcOracle);
          command.execute();
          System.out.println("(v) " + oracleConnection.getName() + "(" + jdbcUrl + ")");
        } catch (SQLException sqle) {
          System.err.println("(X) " + oracleConnection.getName() + "(" + jdbcUrl + ") - " + sqle.getMessage());
        } finally {
          jdbcOracle.finalize();
        }
      }
    }
      
  }

}
