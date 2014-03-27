package org.ivran.customjoin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.ivran.customjoin.command.CustomJoinExecutor;
import org.ivran.customjoin.command.SetMsgExecutor;
import org.ivran.customjoin.command.SetPlayerMsgExecutor;

public class CustomJoinPlugin extends JavaPlugin {

  private PluginDescriptionFile pdf;
  private FileConfiguration config;
  private Strings strings;

  public Strings getStrings() {
    return strings;
  }

  @Override
  public void onEnable() {
    strings = new Strings("MessagesBundle");

    config = getConfig();
    config.options().copyDefaults(true);
    pdf = getDescription();

    getCommand("setmessage").setExecutor(new SetMsgExecutor(this));
    getCommand("setplayermessage").setExecutor(new SetPlayerMsgExecutor(this));
    getCommand("customjoin").setExecutor(new CustomJoinExecutor(this));

    getServer().getPluginManager().registerEvents(new JoinLeaveListener(config), this);
    getLogger().info(strings.format("Plugin.Enabled", pdf.getName(), pdf.getVersion()));
  }

  @Override
  public void onDisable() {
    saveConfig();
    getLogger().info(strings.format("Plugin.Disabled", pdf.getName()));
  }
}
