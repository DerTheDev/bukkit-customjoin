package org.ivran.customjoin;

import static org.ivran.customjoin.ResourceHelper.formatMessage;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.ivran.customjoin.command.CustomJoinExecutor;
import org.ivran.customjoin.command.SetMessageExecutor;
import org.ivran.customjoin.command.SetMyMessageExecutor;
import org.ivran.customjoin.command.SetPlayerMessageExecutor;

public class CustomJoinPlugin extends JavaPlugin {

  private static final Logger LOG = Logger.getLogger("CustomJoin");

  private final PluginDescriptionFile pdf;
  private final FileConfiguration config;
  private final FormatManager manager;

  public CustomJoinPlugin() {
    super();
    pdf = getDescription();
    config = getConfig();
    manager = new FormatManager(config);

    config.options().copyDefaults(true);
  }

  @Override
  public void onEnable() {
    getCommand("setjoin").setExecutor(new SetMessageExecutor(config, manager, "join"));
    getCommand("setquit").setExecutor(new SetMessageExecutor(config, manager, "quit"));
    getCommand("setkick").setExecutor(new SetMessageExecutor(config, manager, "kick"));
    getCommand("setmyjoin").setExecutor(new SetMyMessageExecutor(config, manager, "join"));
    getCommand("setmyquit").setExecutor(new SetMyMessageExecutor(config, manager, "quit"));
    getCommand("setplayerjoin").setExecutor(new SetPlayerMessageExecutor(config, manager, "join"));
    getCommand("setplayerjoin").setExecutor(new SetPlayerMessageExecutor(config, manager, "quit"));
    getCommand("customjoin").setExecutor(new CustomJoinExecutor(pdf));

    final JoinLeaveListener listener = new JoinLeaveListener(config, manager);
    getServer().getPluginManager().registerEvents(listener, this);
    LOG.info(formatMessage("Plugin.Enabled", pdf.getName(), pdf.getVersion()));
  }

  @Override
  public void onDisable() {
    saveConfig();
    LOG.info(formatMessage("Plugin.Disabled", pdf.getName()));
  }
}
