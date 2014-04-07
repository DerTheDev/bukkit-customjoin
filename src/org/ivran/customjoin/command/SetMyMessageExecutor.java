package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatString;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SetMyMessageExecutor extends AbstractExecutor {

  private final FileConfiguration config;
  private final String eventName;

  public SetMyMessageExecutor(FileConfiguration config, String eventName) {
    super();
    addCheck(new PlayerCheck());
    addCheck(new PermissionCheck("customjoin.set.own"));

    this.config = config;
    this.eventName = eventName;
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    String customFormatPath = String.format("custom.%s.%s", eventName.toLowerCase(), sender.getName());

    if (args.length == 0) {
      config.set(customFormatPath, null);
      return formatString("Command.OwnMessageReset", eventName);
    }
    else {
      StringBuilder formatBuilder = new StringBuilder();

      for (String s : args) {
        formatBuilder.append(s).append(' ');
      }

      config.set(customFormatPath, formatBuilder.toString().trim());

      return formatString("Command.OwnMessageSet", eventName);
    }
  }

}
