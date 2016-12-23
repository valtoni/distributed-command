package info.boaventura.distcom.command.action.oracle;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import info.boaventura.distcom.command.RegexTemplate;
import info.boaventura.distcom.oracle.OracleConnection;
import info.boaventura.distcom.oracle.OrafileParser;

@Component
public class ConnectCommand {

  Logger log = LoggerFactory.getLogger(ConnectCommand.class);
  
  static final String CONNECT_PARAM = "$user$/$password$@$instance$";
  
  public static final Path TNSNAMES_FILE = Paths.get(System.getenv("TNS_ADMIN"), "tnsnames.ora");
  
  Map<String, OracleConnection> connections;

  public void parseTns() {
    String tnsfile = TNSNAMES_FILE.toString();
    if (!new File(tnsfile).exists()) {
      throw new RuntimeException("TNS_NAMES file " + tnsfile + " does not found. If you not have TNS_ADMIN environment"
          + " variable defined, please define it");
    }
    log.debug("Tnsfile {} found in this machine", tnsfile);
    try {
      OrafileParser parser = new OrafileParser(tnsfile);
      connections = parser.getConnections();
    } catch (RuntimeException e) {
      log.error("File {} cannot be parsed. Check your tnsfile for errors", tnsfile);
      throw e;
    }
  }
  
  public Map<String, OracleConnection> getConnections() {
    return this.connections;
  }
  
  public JdbcOracle connect(String param) {
    RegexTemplate t = new RegexTemplate(CONNECT_PARAM);
    OracleConnection connection = connections.get(t.get("instance"));
    return create(connection, t.get("user"), t.get("password"));
  }
  
  private JdbcOracle create(OracleConnection oracleConnection, String user, String password) {
    JdbcOracle jdbcOracle = null;

    for (String jdbcUrl: oracleConnection.getJdbcUrls()) {
      // Try to load oracle jdbc driver
      try {
        jdbcOracle = new JdbcOracle(jdbcUrl);
      } catch (SQLException e) {
        throw new RuntimeException("Cannot found Oracle driver");
      }
      // Try open connection to this instance
      try {
        jdbcOracle.openConnection(user, password);
      } catch (SQLException sqle) {
        throw new RuntimeException("Cannot open connection to " + oracleConnection.getName() + "(" + jdbcUrl + "): " + sqle.getMessage());
      }
    }

    // Returns a opened connection
    return jdbcOracle;
      
  }
  
}
