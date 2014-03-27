package org.ivran.customjoin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.MessageType;
import org.ivran.customjoin.Strings;

public class SetMsgExecutor extends AbstractExecutor implements ICommandCheck {

  private final Strings strings;
  private final FileConfiguration config;

  public SetMsgExecutor(CustomJoinPlugin plugin) {
    super(plugin,
        new PermissionCheck("customjoin.set"),
        new ArgumentCountCheck(1, -1));

    addCheck(this);

    this.strings = plugin.getStrings();
    this.config = plugin.getConfig();
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    try {
      MessageType.valueOf(args[0].toUpperCase());
    }
    catch (IllegalArgumentException e) {
      throw new CheckException(strings.format("Command.UnknownMessageType", args[0]));
    }
  }

  @Override
  protected String getError(CommandSender sender, Command cmd, String[] args) {
    return null;
  }

  @Override
  protected void execute(CommandSender sender, Command cmd, String[] args) {
    StringBuilder messageBuilder = new StringBuilder();

    for (int i = 1; i < args.length; i++) {
      messageBuilder.append(args[i]).append(' ');
    }

    config.set(String.format("format.%s", args[0]), messageBuilder.toString().trim());
    sender.sendMessage(ChatColor.GRAY + strings.format("Command.MessageSet", args[0]));
  }
}
