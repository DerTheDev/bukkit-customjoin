package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatString;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SetPlayerMessageExecutor extends AbstractExecutor {

  private final FileConfiguration config;
  private final String eventName;

  public SetPlayerMessageExecutor(FileConfiguration config, String eventName) {
    super();
    addCheck(new PermissionCheck("customjoin.set"));
    addCheck(new ArgumentCountCheck(1, -1));

    this.config = config;
    this.eventName = eventName;
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    String playerName = args[0];
    String customFormatPath = String.format("custom.%s.%s", eventName.toLowerCase(), playerName);

    if (args.length == 1) {
      config.set(customFormatPath, null);
      return formatString("Command.PlayerMessageReset", playerName, eventName);
    }
    else {
      StringBuilder formatBuilder = new StringBuilder();

      for (int i = 1; i < args.length; i++) {
        formatBuilder.append(args[i]).append(' ');
      }

      config.set(customFormatPath, formatBuilder.toString().trim());

      return formatString("Command.PlayerMessageSet", playerName, eventName);
    }
  }

}
