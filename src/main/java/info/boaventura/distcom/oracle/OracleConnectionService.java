package info.boaventura.distcom.oracle;

import info.boaventura.distcom.common.Empty;

import java.util.ArrayList;
import java.util.List;

public class OracleConnectionService extends OracleConnection {

  public static String JDBC_THIN_SERVICE_URL = "jdbc:oracle:thin:@//%s:%d/%s";
  
  public OracleConnectionService(String name, List<String> hosts, int port, String service, BaseType baseType) {
    super(name, hosts, port, baseType);
    Empty.checkRaise(service, "At most one service must be informed");
    this.service = service;
  }

  
  public OracleConnectionService(String name, List<String> hosts, String service, BaseType baseType) {
    super(name, hosts, baseType);
    Empty.checkRaise(host, "At most one service must be informed");
    this.service = service;
  }

  private String service;
  private String host;
  
  public List<String> getJdbcUrls() {
    List<String> jdbcUrls = new ArrayList<String>();
    for (String host: getHosts()) {
      jdbcUrls.add(String.format(JDBC_THIN_SERVICE_URL, host, port, service));
    }
    return jdbcUrls;
  }
  
  public void sanityCheck() {
    super.sanityCheck();
    Empty.checkRaise(service, "A service must be informed for clustered Oracle Connection");
    Empty.checkRaise(host, "A host must be informed for clustered Oracle Connection");
  }

  
}
