package info.boaventura.distcom.provider;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HistoryFileNameProvider extends DefaultHistoryFileNameProvider {
 
  public String getHistoryFileName() {
    return "commander_history.log";
  }

  public String getProviderName() {
    return "The Commander file name provider";
  }
  
}