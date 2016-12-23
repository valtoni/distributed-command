package info.boaventura.distcom.oracle;

import info.boaventura.distcom.common.Empty;

import java.util.ArrayList;
import java.util.List;

public abstract class OracleConnection implements Comparable<OracleConnection> {

  public static int DEFAULT_PORT = 1521;

  public OracleConnection(String name, List<String> hosts, int port, BaseType baseType) {
    this.hosts = hosts;
    this.name = name;
    this.port = port;
    this.baseType = baseType;
    sanityCheck();
  }

  
  public OracleConnection(String name, List<String> hosts, BaseType baseType) {
    this.hosts = hosts;
    this.name = name;
    this.baseType = baseType;
    sanityCheck();
  }

  BaseType baseType;

  int port;
  
  String name;

  private List<String> hosts = new ArrayList<String>();
  
  public int getPort() {
    return port;
  }

  public BaseType getBaseType() {
    return baseType;
  }
  
  public String getName() {
    return name;
  }
  
  public abstract List<String> getJdbcUrls();

  public void sanityCheck() {
    Empty.checkRaise(port, "Connection must have a port to connect");
  }

  public List<String> getHosts() {
    return hosts;
  }
  
  public int compareTo(OracleConnection instance) {
    if (instance == null) {
      return -1;
    }
    return name.toLowerCase().compareTo(instance.name.toLowerCase());
  }

}
