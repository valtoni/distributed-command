package info.boaventura.distcom.oracle;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.RecognitionException;

import edu.gatech.gtri.orafile.Orafile;
import edu.gatech.gtri.orafile.OrafileDef;
import edu.gatech.gtri.orafile.OrafileDict;

public class OrafileParser {

  private ConnectionBuilder builder;
  
  private String file;
  
  private Map<String, OracleConnection> connections;

  public OrafileParser(Path path) {
    this.file = path.toString();
    parse();
  }
  
  public OrafileParser(String file) {
    this.file = file;
    parse();
  }
  
  private String readFile(String file) throws IOException {
    RandomAccessFile aFile = new RandomAccessFile(file, "r");
    FileChannel inChannel = aFile.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    StringBuffer strBuffer = new StringBuffer();
    while(inChannel.read(buffer) > 0) {
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.remaining() > 0) {
              strBuffer.append((char)buffer.get());                
            }
        }
        buffer.clear(); 
    }
    inChannel.close();
    aFile.close();
    return strBuffer.toString();
  }
  
  private void addSid(OrafileDef orafileDef) {
    List<Map<String, String>> values = orafileDef.getVal().findParamAttrs("address", Arrays.asList("host", "port", "sid"));
    String name = null;
    List<String> hosts = new ArrayList<String>();
    Integer port = null;
    String instance = null;
    for (Map<String, String> value: values) {
      name = orafileDef.getName();
      hosts.add(value.get("host"));
      port = value.get("port") == null ? null : Integer.valueOf(value.get("port"));
      instance = value.get("sid");
    }
    if (instance != null) {
      builder.addSid(name, hosts, port, instance, BaseType.SINGLE);
    }
  }
  
  private void addService(OrafileDef orafileDef) {
    List<Map<String, String>> values = orafileDef.getVal().findParamAttrs("address", Arrays.asList("host", "port", "service_name"));
    String name = null;
    List<String> hosts = new ArrayList<String>();
    Integer port = null;
    String service = null;
    for (Map<String, String> value: values) {
      name = orafileDef.getName();
      hosts.add(value.get("host"));
      port = value.get("port") == null ? null : Integer.valueOf(value.get("port"));
      service = value.get("service_name");
    }
    if (service != null) {
      builder.addService(name, hosts, port, service, BaseType.CLUSTER);
    }
  }  
  
  public Map<String, OracleConnection> getConnections() {
    return this.connections;
  }
  
  public void parse() {
    try {
      OrafileDict tns = Orafile.parse(readFile(file));
      List<OrafileDef> listDefs = tns.getNamedParams();
      builder = new ConnectionBuilder();
      for (OrafileDef oraDef: listDefs) {
        addSid(oraDef);
        addService(oraDef);
      }
      this.connections = builder.getList();
    } catch (RecognitionException e) {
      throw new RuntimeException("Orafile cannot be recognized. Detail: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new RuntimeException("Cannot open file '" + file + "'. Detail: " + e.getMessage(), e);
    }
  }
  
  public static void main(String[] args) throws IOException {
    OrafileParser parser = new OrafileParser(args[0]);
    for (OracleConnection connection: parser.getConnections().values()) {
      System.out.println("-------------------------------------------------");
      System.out.println("Name: " + connection.getName());
      System.out.println("Type: " + connection.getBaseType());
      for (String jdbc: connection.getJdbcUrls()) {
        System.out.println(jdbc);
      }
    }
  }
  
}
