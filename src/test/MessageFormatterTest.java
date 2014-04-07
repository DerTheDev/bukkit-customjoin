package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.ivran.customjoin.MessageFormatter;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MessageFormatterTest {

  @Mock private Player player;
  @Mock private FileConfiguration config;

  public MessageFormatterTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFormatter() {
    when(player.getName()).thenReturn("Steve");
    when(player.getDisplayName()).thenReturn("Steve");

    MessageFormatter formatter = new MessageFormatter(config);

    assertEquals("Steve has joined", formatter.format("%player has joined", player));
  }

}
