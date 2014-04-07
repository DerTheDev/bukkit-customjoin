package org.ivran.customjoin;

import java.util.ResourceBundle;

public class ResourceHelper {

  private static final ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle");

  public static String formatString(String key, Object... args) {
    return String.format(getString(key), args);
  }

  public static String getString(String key) {
    if (bundle.containsKey(key)) {
      return bundle.getString(key);
    }
    else {
      return key;
    }
  }

}
