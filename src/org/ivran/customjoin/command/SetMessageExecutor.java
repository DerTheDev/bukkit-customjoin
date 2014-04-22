package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getMessage;
import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getColor;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.FormatManager;

public class SetMessageExecutor extends SetMessageBase {

  private final FormatManager manager;
  private final String eventName;

  public SetMessageExecutor(FileConfiguration config, FormatManager manager, String eventName) {
    super();
    addCheck(new PermissionCheck("customjoin.set"));

    if (config.getBoolean("require-player-name.set")) {
      addCheck(new ArgumentsContainCheck("%player", getMessage("Command.PlayerNameNotFound")));
    }

    this.manager = manager;
    this.eventName = eventName;
  }

  @Override
  protected String saveFormat(CommandSender sender, String format, String[] args) {
    manager.setFormat(eventName, format);

    if (format == null) {
      return getColor("Success") + formatMessage("Command.MessageReset", eventName);
    }
    else {
      return getColor("Success") + formatMessage("Command.MessageSet", eventName);
    }
  }



}
