package info.boaventura.distcom.command;

import org.junit.Test;
import org.springframework.shell.core.CommandResult;

public class GeneralControllerTest extends AbstractShellIntegrationTest {

  @Test
  public void listDbTest() {
    CommandResult cr = getShell().executeCommand("list db --order oi");
    System.out.println(cr.getResult().toString());
  }
  
}
