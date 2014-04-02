package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.R;

public class CustomJoinExecutor extends AbstractExecutor {

  private CustomJoinPlugin plugin;
  private PluginDescriptionFile pdf;

  public CustomJoinExecutor(CustomJoinPlugin plugin) {
    this.plugin = plugin;
    this.pdf = plugin.getDescription();
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    plugin.reloadConfig();
    return R.get("Color.Default") + R.format("Plugin.Reloaded", pdf.getName(), pdf.getVersion());
  }

}
