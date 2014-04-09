package org.ivran.customjoin;

import org.bukkit.configuration.file.FileConfiguration;

public class FormatManager {

  private final FileConfiguration config;

  public FormatManager(FileConfiguration config) {
    this.config = config;
  }

  public void setFormat(String type, String format) {
    config.set(("format." + type), format);
  }

  public void setFormat(String type, String playerName, String format) {
    config.set(("custom." + type + "." + playerName), format);
  }

  /**
   * Retrieves the appropriate message format for the given event parameters.
   * 
   * @param type
   *          The type of the event; Either one of "join", "quit" or "kick" is
   *          expected.
   * @param player
   *          The name of the player involved in the event.
   * @return The message format for this event, or {@code null} if the message
   *         should be omitted.
   */
  public String getFormat(String type, String playerName) {
    String format = config.getString(getConfigPath(type, playerName));

    if (format != null && !format.equalsIgnoreCase("none")) {
      return format;
    }
    else {
      return null;
    }
  }

  private String getConfigPath(String type, String playerName) {
    String customPath = ("custom." + type + "." + playerName);
    if (config.isSet(customPath)) {
      return customPath;
    }

    return String.format("format.%s", type);
  }
}
