package org.ivran.customjoin;

import org.bukkit.configuration.file.FileConfiguration;

public class FormatManager {

  private final FileConfiguration formats;

  public FormatManager(FileConfiguration formats) {
    this.formats = formats;
  }

  public void setFormat(String type, String format) {
    formats.set(("format." + type), format);
  }

  public void setFormat(String type, String playerName, String format) {
    formats.set(("custom." + type + "." + playerName), format);
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
    String path = ("custom." + type + "." + playerName);
    if (!formats.isSet(path)) {
      path = "format." + type;
    }

    String format = formats.getString(path);

    if (format != null && !format.equalsIgnoreCase("none")) {
      return format;
    }
    else {
      return null;
    }
  }

}
