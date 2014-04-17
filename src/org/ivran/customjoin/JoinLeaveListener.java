package org.ivran.customjoin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

  private final FileConfiguration config;
  private final FormatManager manager;
  private final MessageFormatter formatter;

  public JoinLeaveListener(FileConfiguration config, FormatManager manager) {
    this.config = config;
    this.manager = manager;
    formatter = new MessageFormatter();
  }

  private String getPlayerName(Player player) {
    if (config.getBoolean("force-real-name")) {
      return player.getName();
    }
    else {
      return player.getDisplayName();
    }
  }

  private String fetchMessage(String eventType, Player player, String reason) {
    return formatter.format(manager.getFormat(eventType, player.getName()), getPlayerName(player), reason);
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    if (event.getJoinMessage() == null) {
      return;
    }

    event.setJoinMessage(fetchMessage("join", event.getPlayer(), null));
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (event.getQuitMessage() == null) {
      return;
    }

    event.setQuitMessage(fetchMessage("quit", event.getPlayer(), null));
  }

  @EventHandler
  public void onKick(PlayerKickEvent event) {
    if (event.getLeaveMessage() == null) {
      return;
    }

    event.setLeaveMessage(fetchMessage("kick", event.getPlayer(), event.getReason()));
  }
}
