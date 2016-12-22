package info.boaventura.distcom.common;

import java.util.Collection;

public final class Empty {

  private static final boolean check(Collection<?> collection) {
    return collection.size() == 0;
  }
  
  private static final boolean check(String string) {
    return string.equals("");
  }
  
  public static final boolean check(Object object) {
    if (object == null) {
      return false;
    }
    if (object instanceof String) {
      return check((String)object);
    }
    else if (object instanceof Collection) {
      return check((Collection<?>)object);
    }
    else if (object instanceof Integer) {
      return false;
    }
    throw new RuntimeException("Object of type '" + object.getClass().getSimpleName() + "' isn't registered to check empty values");
  }
  
  public static final void checkRaise(Object object, String message) {
    if (check(object)) {
      throw new RuntimeException(message);
    }
  }

  
}
