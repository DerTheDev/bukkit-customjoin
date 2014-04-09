package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getColor;

import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatManager;

public class SetMyMessageExecutor extends SetMessageBase {

  private final FormatManager manager;
  private final String eventName;

  public SetMyMessageExecutor(FormatManager manager, String eventName) {
    super();
    addCheck(new PlayerCheck());
    addCheck(new PermissionCheck("customjoin.set.own"));

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
