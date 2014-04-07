package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatMessage;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SetMyMessageExecutor extends SetMessageBase {

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
  protected String saveFormat(CommandSender sender, String format, String[] args) {
    String customFormatPath = String.format("custom.%s.%s", eventName.toLowerCase(), sender.getName());

    if (format == null) {
      config.set(customFormatPath, null);
      return formatMessage("Command.OwnMessageReset", eventName);
    }
    else {
      config.set(customFormatPath, format);
      return formatMessage("Command.OwnMessageSet", eventName);
    }
  }

}
