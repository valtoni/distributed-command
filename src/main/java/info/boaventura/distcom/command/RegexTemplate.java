package info.boaventura.distcom.command;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTemplate {

  private Map<String, Integer> params = new TreeMap<String, Integer>();
  
  private Pattern p;
  
  private Matcher m;
  
  private String expression = "";
  
  public static void main(String[] args) {
    Matcher m = Pattern.compile("(\\$\\w+\\$)+").matcher("$usuario$/$senha$");
    while (m.find()) {
      System.out.println(m.group());
    }
    System.out.println("IT'S OVER");
    
  }
  
  public Set<String> getParams() {
    return params.keySet();
  }
  
  public RegexTemplate(String template) {
    String targetMatcher = new String(template);
    Matcher m = Pattern.compile("\\$\\w+\\$").matcher(template);
    //Matcher m = Pattern.compile("\\w+").matcher(template);
    if (m.hitEnd()) {
      throw new RuntimeException("No variable of type $text$ was found in " + template);
    }
    m.reset();
    
    String var;
    int i = 0;
    while (m.find()) {
      var = m.group();
      if (params.containsValue(var)) {
        throw new RuntimeException("Variable '" + var + "' already exists in list");
      }
      params.put(var.replaceAll("\\$", ""), ++i);
      targetMatcher = targetMatcher.replace(var, "(.*)");
    }
    this.p = Pattern.compile(targetMatcher);
  }
  
  public void setExpression(String expression) {
    if (!this.expression.equals(expression)) {
      this.expression = expression;
      this.m = p.matcher(this.expression);
      if (!this.m.matches()) {
        throw new RuntimeException("The expression " + expression + " doesn't match " + this.expression);
      }
    }
  }
  
  public String get(String parameter) {
    Integer loc = params.get(parameter);
    if (loc == null) {
      throw new RuntimeException("Paramater " + parameter + " was not found");
    }
    m.reset();
    if (m.find()) {
      return m.group(loc);
    }
    return null;
  }
  
}
