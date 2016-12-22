package info.boaventura.distcom.oracle;

import info.boaventura.distcom.common.Empty;

import java.util.ArrayList;
import java.util.List;

public class OracleConnectionSid extends OracleConnection {

  public static String JDBC_THIN_SID_URL = "jdbc:oracle:thin:@%s:%d:%s";
  
  public OracleConnectionSid(String name, List<String> hosts, int port, String instance, BaseType baseType) {
    super(name, hosts, port, baseType);
    this.instance = instance;
    sanityCheck();
  }

  public OracleConnectionSid(String name, List<String> hosts, String instance, BaseType baseType) {
    super(name, hosts, baseType);
    this.instance = instance;
    sanityCheck();
  }
  
  private String instance;

  public List<String> getJdbcUrls() {
    List<String> jdbcUrls = new ArrayList<String>();
    for (String host: getHosts()) {
      jdbcUrls.add(String.format(JDBC_THIN_SID_URL, host, port, instance));
    }
    return jdbcUrls;
  }
  
  public void sanityCheck() {
    super.sanityCheck();
    Empty.checkRaise(getHosts(), "At most one host must be informed to Connection Sid");
    Empty.checkRaise(getHosts(), "An instance must be informed to Connection Sid");
  }
  
}
