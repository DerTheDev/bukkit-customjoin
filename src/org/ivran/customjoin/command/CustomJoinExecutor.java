package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.ivran.customjoin.R;

public class CustomJoinExecutor extends AbstractExecutor {

  private final PluginDescriptionFile pdf;

  public CustomJoinExecutor(PluginDescriptionFile pdf) {
    this.pdf = pdf;
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    return R.get("Color.Default") + R.format("Plugin.Info", pdf.getName(), pdf.getVersion());
  }

}
