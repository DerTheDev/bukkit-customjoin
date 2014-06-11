package org.ivran.customjoin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigConverter {

  private final FileConfiguration oldConfig;
  private final FileConfiguration formats;
  private boolean modified;

  public ConfigConverter(FileConfiguration oldConfig, FileConfiguration formats) {
    this.oldConfig = oldConfig;
    this.formats = formats;
    modified = false;
  }

  /**
   * @return {@code true} if modifications were made.
   */
  public boolean convert() {
    move("format.join", "default.join");
    move("format.quit", "default.quit");
    move("format.kick", "default.kick");

    oldConfig.set("format", null);

    moveCustomFormats();
    
    return modified;
  }
  
  private void move(String oldPath, String newPath) {
    if (oldConfig.isString(oldPath)) {
      formats.set(newPath, oldConfig.getString(oldPath));
      oldConfig.set(oldPath, null);
      modified = true;
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
    modified = true;
  }

}
