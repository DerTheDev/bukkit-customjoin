package org.ivran.customjoin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MessageFormatter {

  private FileConfiguration config;

  public MessageFormatter(FileConfiguration config) {
    this.config = config;
  }

  public String format(String format, Player player) {
    return format(format, player, null);
  }

  public String format(String format, Player player, String reason) {
    if (format == null) {
      return null;
    }

    String message = format.replace("%player", getPlayerName(player));

    if (reason != null) {
      message = message.replace("%reason", reason);
    }

    return FormatCodes.applyAll(message);
  }

  private String getPlayerName(Player player) {
    if (config.getBoolean("force-real-name")) {
      return player.getName();
    }
    else {
      return player.getDisplayName();
    }
  }
}
