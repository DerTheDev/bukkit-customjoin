package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.MessageType;
import org.ivran.customjoin.R;

public class SetMsgExecutor extends AbstractExecutor implements ICommandCheck {

  private final FileConfiguration config;

  public SetMsgExecutor(CustomJoinPlugin plugin) {
    super(plugin,
        new PermissionCheck("customjoin.set"),
        new ArgumentCountCheck(1, -1));

    addCheck(this);

    this.config = plugin.getConfig();
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    try {
      MessageType.valueOf(args[0].toUpperCase());
    }
    catch (IllegalArgumentException e) {
      throw new CheckException(R.format("Command.UnknownMessageType", args[0]));
    }
  }

  @Override
  protected void execute(CommandSender sender, Command cmd, String[] args) {
    StringBuilder messageBuilder = new StringBuilder();

    for (int i = 1; i < args.length; i++) {
      messageBuilder.append(args[i]).append(' ');
    }

    String message = messageBuilder.toString().trim();

    if (!sender.hasPermission("customjoin.colors") && FormatCodes.containsColors(message)) {
      message = FormatCodes.stripColors(message);
      sender.sendMessage(R.get("Color.Warning") + R.get("Command.MessageSet.ColorsRemoved"));
    }

    if (!sender.hasPermission("customjoin.formats") && FormatCodes.containsFormats(message)) {
      message = FormatCodes.stripFormats(message);
      sender.sendMessage(R.get("Color.Warning") + R.get("Command.MessageSet.FormatsRemoved"));
    }

    config.set(String.format("format.%s", args[0]), message);
    sender.sendMessage(R.get("Color.Success") + R.format("Command.MessageSet", args[0]));
  }
}
