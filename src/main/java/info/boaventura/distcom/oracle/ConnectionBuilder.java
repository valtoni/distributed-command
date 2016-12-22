package info.boaventura.distcom.oracle;

import java.util.ArrayList;
import java.util.List;

public final class ConnectionBuilder {

  private List<OracleConnection> list = new ArrayList<OracleConnection>();

  public List<OracleConnection> getList() {
    return list;
  }

  public ConnectionBuilder addSid(String name, List<String> hosts, String instance, BaseType baseType) {
    list.add(new OracleConnectionSid(name, hosts, instance, baseType));
    return this;
  }

  public ConnectionBuilder addSid(String name, List<String> hosts, int port, String instance, BaseType baseType) {
    list.add(new OracleConnectionSid(name, hosts, port, instance, baseType));
    return this;
  }
  
  public ConnectionBuilder addService(String name, List<String> hosts, String service, BaseType baseType) {
    list.add(new OracleConnectionService(name, hosts, service, baseType));
    return this;
  }

  public ConnectionBuilder addService(String name, List<String> hosts, int port, String service, BaseType baseType) {
    list.add(new OracleConnectionService(name, hosts, port, service, baseType));
    return this;
  }

  
  
}
