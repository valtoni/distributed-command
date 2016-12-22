package info.boaventura.distcom.command.oracle;

public class OracleCommandDoConnection extends OracleSingleCommandAbstract implements OracleCommand {

  private static final String CMD_SINGLE = "SELECT * FROM DUAL";

  public OracleCommandDoConnection() {
    this.commandTemplate = CMD_SINGLE;
  }
  
  
}
