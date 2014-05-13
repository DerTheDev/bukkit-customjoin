package org.ivran.customjoin;

import static org.ivran.customjoin.ResourceHelper.formatMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
  private final File formatsFile;
  private final FileConfiguration formats;
  private final FormatManager manager;

  public CustomJoinPlugin() {
    super();
    pdf = getDescription();

    saveDefaultConfig();
    config = getConfig();
    config.options().copyDefaults(true);

    formatsFile = new File(getDataFolder(), "formats.yml");
    if (!formatsFile.exists()) {
      writeDefaultFormats(formatsFile);
    }

    formats = YamlConfiguration.loadConfiguration(formatsFile);
    manager = new FormatManager(formats);
  }

  private void writeDefaultFormats(File file) {
    try (
        OutputStream out = new FileOutputStream(file);
        InputStream in = getResource(file.getName())) {

      int i;
      while ((i = in.read()) != -1) {
        out.write(i);
      }

      out.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onEnable() {
    getCommand("setjoin").setExecutor(new SetMessageExecutor(config, manager, "join"));
    getCommand("setquit").setExecutor(new SetMessageExecutor(config, manager, "quit"));
    getCommand("setkick").setExecutor(new SetMessageExecutor(config, manager, "kick"));
    getCommand("setmyjoin").setExecutor(new SetMyMessageExecutor(config, manager, "join"));
    getCommand("setmyquit").setExecutor(new SetMyMessageExecutor(config, manager, "quit"));
    getCommand("setplayerjoin").setExecutor(new SetPlayerMessageExecutor(config, manager, "join"));
    getCommand("setplayerquit").setExecutor(new SetPlayerMessageExecutor(config, manager, "quit"));
    getCommand("customjoin").setExecutor(new CustomJoinExecutor(pdf));

    final JoinLeaveListener listener = new JoinLeaveListener(config, manager);
    getServer().getPluginManager().registerEvents(listener, this);
    LOG.info(formatMessage("Plugin.Enabled", pdf.getName(), pdf.getVersion()));
  }

  @Override
  public void onDisable() {
    try {
      formats.save(formatsFile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    LOG.info(formatMessage("Plugin.Disabled", pdf.getName()));
  }
}
