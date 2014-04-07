package org.ivran.customjoin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FormatProvider {

  private final FileConfiguration config;

  public FormatProvider(FileConfiguration config) {
    this.config = config;
  }

  /**
   * Retrieves the appropriate message format for the given event parameters.
   * 
   * @param type
   *          The type of the event; Either one of "join", "quit" or "kick" is
   *          expected.
   * @param player
   *          The player involved in the event.
   * @return The message format for this event, or {@code null} if the message
   *         should be omitted.
   */
  public String getFormat(String type, Player player) {
    String format = config.getString(getConfigPath(type, player));

    if (format != null && !format.equalsIgnoreCase("none")) {
      return format;
    }
    else {
      return null;
    }
  }

  private String getConfigPath(String type, Player player) {
    String customPath = String.format("custom.%s.%s", type, player.getName());
    if (config.isSet(customPath)) {
      return customPath;
    }

    return String.format("format.%s", type);
  }
}
