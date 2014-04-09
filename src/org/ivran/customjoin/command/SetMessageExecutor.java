package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatMessage;

import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatManager;

public class SetMessageExecutor extends SetMessageBase {

  private final FormatManager manager;
  private final String eventName;

  public SetMessageExecutor(FormatManager manager, String eventName) {
    super();
    addCheck(new PermissionCheck("customjoin.set"));

    this.manager = manager;
    this.eventName = eventName;
  }

  @Override
  protected String saveFormat(CommandSender sender, String format, String[] args) {
    manager.setFormat(eventName, format);

    if (format == null) {
      return formatMessage("Command.MessageReset", eventName);
    }
    else {
      return formatMessage("Command.MessageSet", eventName);
    }
  }

}
