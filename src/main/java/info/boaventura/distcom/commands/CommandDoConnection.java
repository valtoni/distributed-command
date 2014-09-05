package info.boaventura.distcom.commands;

public class CommandDoConnection extends SingleCommandAbstract implements Command {

  private static final String CMD_SINGLE = "SELECT * FROM DUAL";

  public CommandDoConnection() {
    this.commandTemplate = CMD_SINGLE;
  }
  
  
}
