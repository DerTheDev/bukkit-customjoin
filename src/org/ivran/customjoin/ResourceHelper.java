package org.ivran.customjoin;

import java.util.ResourceBundle;

public class ResourceHelper {

  private static final ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle");

  public static String formatMessage(String key, Object... args) {
    return String.format(getMessage(key), args);
  }

  public static String getMessage(String key) {
    if (bundle.containsKey(key)) {
      return bundle.getString(key);
    }
    else {
      return key;
    }
  }

}
