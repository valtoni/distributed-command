package info.boaventura.distcom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import info.boaventura.distcom.command.oracle.OracleCommandChangePassword;

public class Main {

  private static String getValue(String title) {
    System.out.print(title);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      return br.readLine();
   } catch (IOException ioe) {
      throw new RuntimeException("IO error trying to read your name!");
   }
  }
  
  public static void main(String[] args) {
    String tnsnames = getValue("Localization of file tnsnames.ora: ");
    String user = getValue("Username: ");
    String password = getValue("Password: ");
    String newPassword = getValue("New password: ");

    Commander commander = new Commander(tnsnames, user, password);
    
    OracleCommandChangePassword commandChangePassword = new OracleCommandChangePassword();
    commandChangePassword.set("user", user);
    commandChangePassword.set("password", password);
    commandChangePassword.set("newpassword", newPassword);
    
    commander.execute(commandChangePassword);

  }

}
