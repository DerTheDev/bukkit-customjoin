package org.ivran.customjoin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.Strings;

public class CustomJoinExecutor implements CommandExecutor {

  private CustomJoinPlugin plugin;
  private PluginDescriptionFile pdf;
  private final Strings strings;

  public CustomJoinExecutor(CustomJoinPlugin plugin) {
    this.plugin = plugin;
    this.pdf = plugin.getDescription();
    this.strings = plugin.getStrings();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
    plugin.reloadConfig();

    sender.sendMessage(ChatColor.GRAY
        + String.format(strings.format("Plugin.Reloaded", pdf.getName(), pdf.getVersion())));

    return true;
  }

}
