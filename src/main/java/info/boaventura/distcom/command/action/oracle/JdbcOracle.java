package info.boaventura.distcom.command.action.oracle;

import info.boaventura.distcom.common.JdbcUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcOracle {

  private String jdbcUrl;
  
  private Connection conn;
  
  private List<Statement> statements = new ArrayList<Statement>();
  
  public JdbcOracle(String jdbcUrl) throws SQLException {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    this.jdbcUrl = jdbcUrl;
  }
  
  public Connection openConnection(String username, String password) throws SQLException {
    conn = DriverManager.getConnection(jdbcUrl, username, password);
    return conn;
  }
  
  public Statement createStatement() throws SQLException {
    Statement statement = conn.createStatement();
    statements.add(statement);
    return statement;
  }
  
  public boolean execStatement(String sql) throws SQLException {
    boolean executed;
    Statement statement = null;
    try {
      statement = conn.createStatement();
      executed = statement.execute(sql);
    } catch (SQLException sqle) {
      throw sqle;
    } finally {
      JdbcUtil.close(statement);
    }
    return executed;
  }
  
  public void finalize() {
    for (Statement statement: statements) {
      JdbcUtil.close(statement);
    }
    JdbcUtil.close(conn);
  }
  
}
