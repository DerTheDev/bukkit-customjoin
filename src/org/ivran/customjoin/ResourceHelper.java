package org.ivran.customjoin;

import java.util.ResourceBundle;

import org.bukkit.ChatColor;

public class ResourceHelper {

  private static final ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle");

  public static String formatMessage(String key, Object... args) {
    return String.format(getMessage(key), args);
  }

  public static ChatColor getColor(String key) {
    return ChatColor.getByChar(bundle.getString("Color." + key));
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
