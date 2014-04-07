package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatString;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SetMessageExecutor extends AbstractExecutor {

  private final FileConfiguration config;
  private final String configNode;
  private final String eventName;

  public SetMessageExecutor(FileConfiguration config, String configNode, String eventName) {
    super();
    addCheck(new PermissionCheck("customjoin.set"));

    this.config = config;
    this.configNode = configNode;
    this.eventName = eventName;
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    if (args.length == 0) {
      config.set(configNode, null);
      return formatString("Command.MessageReset", eventName);
    }
    else {
      StringBuilder formatBuilder = new StringBuilder();

      for (String s : args) {
        formatBuilder.append(s).append(' ');
      }

      config.set(configNode, formatBuilder.toString().trim());

      return formatString("Command.MessageSet", eventName);
    }
  }

}
