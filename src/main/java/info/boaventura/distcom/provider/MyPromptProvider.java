package info.boaventura.distcom.provider;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyPromptProvider extends DefaultPromptProvider {
  
  @Override
  public String getPrompt() {
    return "CMD> ";
  }

  @Override
  public String getProviderName() {
    return "The Commander prompt provider";
  }
  
}