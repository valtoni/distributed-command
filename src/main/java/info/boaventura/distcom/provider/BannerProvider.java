package info.boaventura.distcom.provider;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BannerProvider extends DefaultBannerProvider {

  public String getBanner() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n    |_   _| |          /  __ \\                                         | |           \n");
    sb.append("      | | | |__   ___  | /  \\/ ___  _ __ ___  _ __ ___   __ _ _ __   __| | ___ _ __  \n");
    sb.append("      | | | '_ \\ / _ \\ | |    / _ \\| '_ ` _ \\| '_ ` _ \\ / _` | '_ \\ / _` |/ _ \\ '__| \n");
    sb.append("      | | | | | |  __/ | \\__/\\ (_) | | | | | | | | | | | (_| | | | | (_| |  __/ |    \n");
    sb.append("      \\_/ |_| |_|\\___|  \\____/\\___/|_| |_| |_|_| |_| |_|\\__,_|_| |_|\\__,_|\\___|_|    \n");
    
    sb.append(OsUtils.LINE_SEPARATOR);
    return sb.toString();
  }

  public String getVersion() {
    return "1.0-SNAPSHOT";
  }

  public String getWelcomeMessage() {
    return "The Commander % Release " + getVersion() + " in " + ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME) +
        "\n\nProudly builded in 2016 by Valtoni Boaventura.\n";
  }

  public String getProviderName() {
    return "Banner The Commander";
  }

}
