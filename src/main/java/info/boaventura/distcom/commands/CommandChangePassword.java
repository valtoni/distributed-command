package info.boaventura.distcom.commands;

import java.util.HashMap;

public class CommandChangePassword extends SingleCommandAbstract implements Command {

  private static final String CMD_CHANGE_PASS = "ALTER USER %s IDENTIFIED BY %s REPLACE %s";
  
  public CommandChangePassword() {
    parameters = new HashMap<String, String>();
    parameters.put("user", null);
    parameters.put("password", null);
    parameters.put("newpassword", null);
    commandTemplate = CMD_CHANGE_PASS;
  }
  
}
