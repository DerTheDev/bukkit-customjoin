package org.ivran.customjoin;

import static org.ivran.customjoin.ResourceHelper.formatMessage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.ivran.customjoin.command.CustomJoinExecutor;
import org.ivran.customjoin.command.SetMessageExecutor;
import org.ivran.customjoin.command.SetMyMessageExecutor;
import org.ivran.customjoin.command.SetPlayerMessageExecutor;

public class CustomJoinPlugin extends JavaPlugin {

  private PluginDescriptionFile pdf;
  private FileConfiguration config;
  private FormatManager manager;

  @Override
  public void onEnable() {
    config = getConfig();
    config.options().copyDefaults(true);
    pdf = getDescription();
    manager = new FormatManager(config);

    getCommand("setjoin").setExecutor(new SetMessageExecutor(manager, "join"));
    getCommand("setquit").setExecutor(new SetMessageExecutor(manager, "quit"));
    getCommand("setkick").setExecutor(new SetMessageExecutor(manager, "kick"));
    getCommand("setmyjoin").setExecutor(new SetMyMessageExecutor(manager, "join"));
    getCommand("setmyquit").setExecutor(new SetMyMessageExecutor(manager, "quit"));
    getCommand("setplayerjoin").setExecutor(new SetPlayerMessageExecutor(manager, "join"));
    getCommand("setplayerjoin").setExecutor(new SetPlayerMessageExecutor(manager, "quit"));
    getCommand("customjoin").setExecutor(new CustomJoinExecutor(pdf));

    getServer().getPluginManager().registerEvents(new JoinLeaveListener(config), this);
    getLogger().info(formatMessage("Plugin.Enabled", pdf.getName(), pdf.getVersion()));
  }

  @Override
  public void onDisable() {
    saveConfig();
    getLogger().info(formatMessage("Plugin.Disabled", pdf.getName()));
  }
}
