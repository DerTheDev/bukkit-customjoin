package org.ivran.customjoin;

public class MessageFormatter {

  public String format(String format, String playerName) {
    return format(format, playerName, null);
  }

  public String format(String format, String playerName, String reason) {
    if (format == null) {
      return null;
    }

    String message = format.replace("%player", playerName);

    if (reason != null) {
      message = message.replace("%reason", reason);
    }

    return FormatCodes.applyAll(message);
  }

}
