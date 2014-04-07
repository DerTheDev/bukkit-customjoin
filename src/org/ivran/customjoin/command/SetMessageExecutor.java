package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatString;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SetMessageExecutor extends SetMessageBase {

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
  protected String saveFormat(CommandSender sender, String format, String[] args) {
    if (format == null) {
      config.set(configNode, null);
      return formatString("Command.MessageReset", eventName);
    }
    else {
      config.set(configNode, format);
      return formatString("Command.MessageSet", eventName);
    }
  }

}
