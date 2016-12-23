package info.boaventura.distcom.command.action.oracle;

import java.util.HashMap;

public class OracleCommandChangePassword extends OracleSingleCommandAbstract implements OracleCommand {

  private static final String CMD_CHANGE_PASS = "ALTER USER %s IDENTIFIED BY %s REPLACE %s";
  
  public OracleCommandChangePassword() {
    parameters = new HashMap<String, String>();
    parameters.put("user", null);
    parameters.put("password", null);
    parameters.put("newpassword", null);
    commandTemplate = CMD_CHANGE_PASS;
  }
  
}
