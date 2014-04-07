package org.ivran.customjoin;

import static org.ivran.customjoin.ResourceHelper.formatString;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.ivran.customjoin.command.CustomJoinExecutor;
import org.ivran.customjoin.command.SetMsgExecutor;
import org.ivran.customjoin.command.SetPlayerMsgExecutor;

public class CustomJoinPlugin extends JavaPlugin {

  private PluginDescriptionFile pdf;
  private FileConfiguration config;

  @Override
  public void onEnable() {
    config = getConfig();
    config.options().copyDefaults(true);
    pdf = getDescription();

    getCommand("setmessage").setExecutor(new SetMsgExecutor(config));
    getCommand("setplayermessage").setExecutor(new SetPlayerMsgExecutor(config));
    getCommand("customjoin").setExecutor(new CustomJoinExecutor(pdf));

    getServer().getPluginManager().registerEvents(new JoinLeaveListener(config), this);
    getLogger().info(formatString("Plugin.Enabled", pdf.getName(), pdf.getVersion()));
  }

  @Override
  public void onDisable() {
    saveConfig();
    getLogger().info(formatString("Plugin.Disabled", pdf.getName()));
  }
}
