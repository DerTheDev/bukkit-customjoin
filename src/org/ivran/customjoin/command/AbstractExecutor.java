package org.ivran.customjoin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.Strings;

public abstract class AbstractExecutor implements CommandExecutor {

  protected FileConfiguration config;
  private final Strings strings;
  private String permission;

  public AbstractExecutor(CustomJoinPlugin plugin, String permission) {
    this.config = plugin.getConfig();
    this.strings = plugin.getStrings();
    this.permission = permission;
  }

  /**
   * Called before {@code execute}. Any and all permission cheking/argument
   * validation should be done here.
   * 
   * @return {@code null} if everything is fine, an error message if the command
   *         cannot/should not be executed.
   */
  protected abstract String getError(CommandSender sender, Command cmd, String[] args);

  /**
   * Called after {@code getError} has ensured that the command can be executed.
   */
  protected abstract void execute(CommandSender sender, Command cmd, String[] args);

  @Override
  public final boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (permission != null && !sender.hasPermission(permission)) {
      sender.sendMessage(ChatColor.RED + strings.get("Command.NoPermission"));
    }
    else {
      String error = getError(sender, cmd, args);

      if (error != null) {
        sender.sendMessage(ChatColor.RED + error);
      }
      else {
        execute(sender, cmd, args);
      }
    }

    return true;
  }
}
