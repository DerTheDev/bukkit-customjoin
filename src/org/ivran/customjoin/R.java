package org.ivran.customjoin;

import java.util.ResourceBundle;

public class R {

  private static final ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle");

  public static String format(String key, Object... args) {
    return String.format(get(key), args);
  }

  public static String get(String key) {
    if (bundle.containsKey(key)) {
      return bundle.getString(key);
    }
    else {
      return key;
    }
  }

}
