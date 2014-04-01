package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.MessageType;
import org.ivran.customjoin.R;

public class SetPlayerMsgExecutor extends AbstractExecutor implements ICommandCheck {

  private final FileConfiguration config;

  public SetPlayerMsgExecutor(CustomJoinPlugin plugin) {
    super(plugin,
        new PermissionCheck("customjoin.setplayermessage"),
        new ArgumentCountCheck(2, -1));

    addCheck(this);

    this.config = plugin.getConfig();
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    try {
      MessageType.valueOf(args[1].toUpperCase());
    }
    catch (IllegalArgumentException e) {
      throw new CheckException(R.format("Command.UnknownMessageType", args[1]));
    }
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    String message = null;

    if (args.length - 2 > 0) {
      StringBuilder messageBuilder = new StringBuilder();

      for (int i = 2; i < args.length; i++) {
        messageBuilder.append(args[i]).append(' ');
      }

      message = messageBuilder.toString().trim();
    }

    if (!sender.hasPermission("customjoin.colors") && FormatCodes.containsColors(message)) {
      message = FormatCodes.stripColors(message);
      sender.sendMessage(R.get("Color.Warning") + R.get("Command.MessageSet.ColorsRemoved"));
    }

    if (!sender.hasPermission("customjoin.formats") && FormatCodes.containsFormats(message)) {
      message = FormatCodes.stripFormats(message);
      sender.sendMessage(R.get("Color.Warning") + R.get("Command.MessageSet.FormatsRemoved"));
    }

    String player = args[0];
    String type = args[1];

    config.set(String.format("custom.%s.%s", type, player), message);

    return R.get("Color.Success")
        + R.format(message == null ? "Command.PlayerMessageDeleted" : "Command.PlayerMessageSet", player, type);
  }
}
