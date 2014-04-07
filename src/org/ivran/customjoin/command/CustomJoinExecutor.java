package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CustomJoinExecutor extends AbstractExecutor {

  private final PluginDescriptionFile pdf;

  public CustomJoinExecutor(PluginDescriptionFile pdf) {
    this.pdf = pdf;
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    return getMessage("Color.Default") + formatMessage("Plugin.Info", pdf.getName(), pdf.getVersion());
  }

}
