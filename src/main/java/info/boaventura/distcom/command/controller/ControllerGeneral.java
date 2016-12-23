package info.boaventura.distcom.command.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import info.boaventura.distcom.command.action.oracle.ConnectCommand;
import info.boaventura.distcom.command.action.oracle.OracleSingleCommandAbstract;
import info.boaventura.distcom.oracle.OracleConnection;

@Component
//@Validated
public class ControllerGeneral extends OracleSingleCommandAbstract implements CommandMarker {

  @Resource
  ConnectCommand connectCommand;
  
  
  @CliAvailabilityIndicator({"connect"})
  public boolean isConnectAvailable() {
    return true;
  }
  
  private boolean isEmpty(String value) {
    return value == null || value.equals("");
  }

  private boolean paramWarn(String param, String... validValues) {
    if (isEmpty(param)) {
      return false;
    }
    StringBuffer values = new StringBuffer();
    for (String validValue: validValues) {
      values.append(values.length() == 0 ? "" : " / " + validValue);
    }
    for (String validValue: validValues) {
      if (!param.equals(validValue)) {
        System.out.println("Value " + param + " will be ignored (valid values: " + values.toString() + ")");
        return false;
      }
    }
    return true;
  }
  
  Function<String, String> add = x -> x + "";
  
  void execute(Function<String, Void> doMethod, String param, String... validValues) {
    if (paramWarn(param, validValues)) {
      doMethod.apply(param);
    }
  }
  
  
  @CliCommand(value = "list db", help = "List databases founded in tnsnames file")
  public String listDb(
      @CliOption(key = "order", help = "Order by item (oi) or order by host (oh)")
      final String order,
      @CliOption(key = "filter", help = "filter with wildcards (*)")
      final String filter) {
    StringBuilder result = new StringBuilder();
    
    
    // Test first if command can have environment tns file fetched
    connectCommand.parseTns();
    
    List<OracleConnection> entries = new ArrayList<OracleConnection>(connectCommand.getConnections().values());

    //execute((a) -> { Collections.sort(entries); }, param, "oi");
    
    if (order != null && order.equals("oi")) {
      Collections.sort(entries);
    }
    
    if (filter != null) {
      String nfilter = filter.replaceAll("\\*", ".*");
      entries = entries.stream()
        .filter(i -> i.getName().toLowerCase().matches(nfilter.toLowerCase()) || 
            i.getHosts().stream().anyMatch(h -> h.toLowerCase().matches(nfilter.toLowerCase())))
        .collect(Collectors.toList());
    }
    
    for (OracleConnection entry: entries) {
      result.append(String.format("[%s] ", entry.getName()));
      for (String host: entry.getHosts()) {
        result.append(String.format("%s\n", host));
      }
    }
    result.append(entries.size() + " entries found.\n");
    return result.toString();
  }

  
  @CliCommand(value = "connect", help = "Connect to a oracle instance. format: user/password@instance")
  public String connect(@CliOption(key = "") final String param) {
    //JdbcOracle jdbc = connectCommand.connect(param);
    return "WITHOUT EFFECT";
  }
  
  
}
