package org.ivran.customjoin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.MessageType;
import org.ivran.customjoin.Strings;

public class SetMsgExecutor extends AbstractExecutor {

  private final Strings strings;
  private final FileConfiguration config;

  public SetMsgExecutor(CustomJoinPlugin plugin) {
    super(plugin, new PermissionCheck("customjoin.set"));
    this.strings = plugin.getStrings();
    this.config = plugin.getConfig();
  }

  @Override
  protected String getError(CommandSender sender, Command cmd, String[] args) {
    if (args.length < 1) {
      return strings.get("Command.SyntaxError");
    }

    try {
      MessageType.valueOf(args[0].toUpperCase());
    }
    catch (IllegalArgumentException e) {
      return strings.format("Command.UnknownMessageType", args[0]);
    }

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
