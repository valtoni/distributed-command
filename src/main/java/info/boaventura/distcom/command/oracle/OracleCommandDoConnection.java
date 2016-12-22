package info.boaventura.distcom.command.oracle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.stringtemplate.StringTemplate;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class OracleCommandDoConnection extends OracleSingleCommandAbstract implements OracleCommand, CommandMarker {

  private static final String CMD_SINGLE = "SELECT * FROM DUAL";

  public OracleCommandDoConnection() {
    this.commandTemplate = CMD_SINGLE;
  }
  
  @CliAvailabilityIndicator({"connect"})
  public boolean isConnectAvailable() {
    return true;
  }
  
  private boolean isEmpty(String value) {
    return value == null || value.equals("");
  }
  
  @CliCommand(value = "connect", help = "Connect to a oracle instance. format: user/password@instance")
  public String connect(@CliOption(key = "") final String param) {
    if (isEmpty(param)) {
      return "ERROR: You must supply directives";
    }
    Matcher matcher = Pattern.compile("(.*)/(.*)@(.*)").matcher(param);
    if (!matcher.matches()) {
      return "ERROR: Unrecognizable param " + param;
    }
    String user = matcher.group(1);
    String pass = matcher.group(2);
    String instance = matcher.group(3);
    return user + " / " + pass + " @ " + instance;
  }
  
  public static void main(String[] args) {
    StringTemplate st = new StringTemplate("$usuario$/$senha$@$instancia$");
    System.out.println(st.getAttribute("usuario"));
  }
  
  
}
