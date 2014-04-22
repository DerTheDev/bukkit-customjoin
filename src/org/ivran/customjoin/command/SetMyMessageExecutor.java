package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getMessage;
import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getColor;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.FormatManager;

public class SetMyMessageExecutor extends SetMessageBase {

  private final FormatManager manager;
  private final String eventName;

  public SetMyMessageExecutor(FileConfiguration config, FormatManager manager, String eventName) {
    super();
    addCheck(new PlayerCheck());
    addCheck(new PermissionCheck("customjoin.set.own"));

    if (config.getBoolean("require-player-name.setmy")) {
      addCheck(new ArgumentsContainCheck("%player", getMessage("Command.PlayerNameNotFound")));
    }

    this.manager = manager;
    this.eventName = eventName;
  }

  @Override
  protected String saveFormat(CommandSender sender, String format, String[] args) {
    manager.setFormat(eventName, sender.getName(), format);

    if (format == null) {
      return getColor("Success") + formatMessage("Command.OwnMessageReset", eventName);
    }
    else {
      return getColor("Success") + formatMessage("Command.OwnMessageSet", eventName);
    }
  }

}
