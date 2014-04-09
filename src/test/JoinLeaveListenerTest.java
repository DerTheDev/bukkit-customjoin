package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.ivran.customjoin.FormatManager;
import org.ivran.customjoin.JoinLeaveListener;
import org.ivran.customjoin.MessageFormatter;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JoinLeaveListenerTest {

  @Mock private FileConfiguration config;
  @Mock private Player player;
  private final FormatManager manager;
  private final JoinLeaveListener listener;
  private final String playerName;
  private final String displayName;

  public JoinLeaveListenerTest() {
    MockitoAnnotations.initMocks(this);

    when(config.getString("format.join")).thenReturn("%player has joined the game");
    when(config.getString("format.quit")).thenReturn("%player has left the game");
    when(config.getString("format.kick")).thenReturn("%player was kicked: %reason");

    manager = new FormatManager(config);
    listener = new JoinLeaveListener(new MessageFormatter(config), manager);

    playerName = "Steve";
    displayName = "Stevenson";

    when(player.getName()).thenReturn(playerName);
    when(player.getDisplayName()).thenReturn(displayName);
  }

  private void setForcePlayerName(boolean forcePlayerName) {
    when(config.getBoolean("force-real-name")).thenReturn(forcePlayerName);
  }

  @Test
  public void testJoin() {
    PlayerJoinEvent event = new PlayerJoinEvent(player, "Steve joined the game");

    setForcePlayerName(false);
    listener.onJoin(event);
    assertEquals(displayName + " has joined the game", event.getJoinMessage());

    setForcePlayerName(true);
    listener.onJoin(event);
    assertEquals(playerName + " has joined the game", event.getJoinMessage());

    event.setJoinMessage(null);
    listener.onJoin(event);
    assertEquals(null, event.getJoinMessage());
  }

  @Test
  public void testQuit() {
    PlayerQuitEvent event = new PlayerQuitEvent(player, "Steve left the game");

    setForcePlayerName(false);
    listener.onQuit(event);
    assertEquals(displayName + " has left the game", event.getQuitMessage());

    setForcePlayerName(true);
    listener.onQuit(event);
    assertEquals(playerName + " has left the game", event.getQuitMessage());

    event.setQuitMessage(null);
    listener.onQuit(event);
    assertEquals(null, event.getQuitMessage());
  }

  @Test
  public void testKick() {
    PlayerKickEvent event = new PlayerKickEvent(player, "For science", "Steve kicked: For science");

    setForcePlayerName(false);
    listener.onKick(event);
    assertEquals(displayName + " was kicked: For science", event.getLeaveMessage());

    setForcePlayerName(true);
    listener.onKick(event);
    assertEquals(playerName + " was kicked: For science", event.getLeaveMessage());

    event.setLeaveMessage(null);
    listener.onKick(event);
    assertEquals(null, event.getLeaveMessage());
  }

}
