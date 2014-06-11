package org.ivran.customjoin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigConverter {

  private final FileConfiguration oldConfig;
  private final FileConfiguration formats;

  public ConfigConverter(FileConfiguration oldConfig, FileConfiguration formats) {
    this.oldConfig = oldConfig;
    this.formats = formats;
  }

  public void convert() {
    move("format.join", "default.join");
    move("format.quit", "default.quit");
    move("format.kick", "default.kick");

    oldConfig.set("format", null);

    moveCustomFormats();
  }

  private void move(String oldPath, String newPath) {
    if (oldConfig.isString(oldPath)) {
      formats.set(newPath, oldConfig.getString(oldPath));
      oldConfig.set(oldPath, null);
    }
  }

  private void moveCustomFormats() {
    if (!oldConfig.isConfigurationSection("custom")) {
      return;
    }

    ConfigurationSection customSection = oldConfig.getConfigurationSection("custom");
    for (String key : customSection.getKeys(true)) {
      formats.set("custom." + key, customSection.getString(key));
    }
    oldConfig.set("custom", null);
  }

}
