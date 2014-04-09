package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatManager;

public class SetPlayerMessageExecutor extends SetMessageBase {

  private final FormatManager config;
  private final String eventName;

  public SetPlayerMessageExecutor(FormatManager config, String eventName) {
    super();
    addCheck(new PermissionCheck("customjoin.set"));
    addCheck(new ArgumentCountCheck(1, -1));

    this.config = config;
    this.eventName = eventName;
  }

  @Override
  protected String createFormat(CommandSender sender, Command cmd, String[] args) {
    if (args.length == 1) {
      return null;
    }
    else {
      StringBuilder formatBuilder = new StringBuilder();

      for (int i = 1; i < args.length; i++) {
        formatBuilder.append(args[i]).append(' ');
      }

      return formatBuilder.toString().trim();
    }
  }

  @Override
  protected String saveFormat(CommandSender sender, String format, String[] args) {
    String playerName = args[0];
    config.setFormat(eventName, args[0], format);

    if (format == null) {
      return formatMessage("Command.PlayerMessageReset", playerName, eventName);
    }
    else {
      return formatMessage("Command.PlayerMessageSet", playerName, eventName);
    }
  }

}
