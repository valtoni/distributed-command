package info.boaventura.distcom.oracle;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class ConnectionBuilder {

  private Map<String, OracleConnection> list = new TreeMap<String, OracleConnection>();

  public Map<String, OracleConnection> getList() {
    return list;
  }

  public ConnectionBuilder addSid(String name, List<String> hosts, String instance, BaseType baseType) {
    list.put(name, new OracleConnectionSid(name, hosts, instance, baseType));
    return this;
  }

  public ConnectionBuilder addSid(String name, List<String> hosts, int port, String instance, BaseType baseType) {
    list.put(name, new OracleConnectionSid(name, hosts, port, instance, baseType));
    return this;
  }
  
  public ConnectionBuilder addService(String name, List<String> hosts, String service, BaseType baseType) {
    list.put(name, new OracleConnectionService(name, hosts, service, baseType));
    return this;
  }

  public ConnectionBuilder addService(String name, List<String> hosts, int port, String service, BaseType baseType) {
    list.put(name, new OracleConnectionService(name, hosts, port, service, baseType));
    return this;
  }

  
  
}
