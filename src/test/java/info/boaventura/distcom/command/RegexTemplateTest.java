package info.boaventura.distcom.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexTemplateTest {

  @Test
  public void testRegexTemplate() {
    RegexTemplate t = new RegexTemplate("$user$/$password$@$instance$");
    t.setExpression("valtoni/p4ssw0rd@ragnar_lothbrok");
    assertEquals("valtoni", t.get("user"));
    assertEquals("p4ssw0rd", t.get("password"));
    assertEquals("ragnar_lothbrok", t.get("instance"));
  }

}
