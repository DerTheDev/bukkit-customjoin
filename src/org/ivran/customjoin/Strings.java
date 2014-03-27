package org.ivran.customjoin;

import java.util.ResourceBundle;

public class Strings {

  private final ResourceBundle bundle;

  public Strings(String resourceBundle) {
    this.bundle = ResourceBundle.getBundle(resourceBundle);
  }

  public String format(String key, Object... args) {
    return String.format(get(key), args);
  }

  public String get(String key) {
    if (bundle.containsKey(key)) {
      return bundle.getString(key);
    }
    else {
      return key;
    }
  }

}
