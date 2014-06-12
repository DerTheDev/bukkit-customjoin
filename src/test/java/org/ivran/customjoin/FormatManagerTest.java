package org.ivran.customjoin;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.FormatManager;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FormatManagerTest {

  @Mock private FileConfiguration config;
  private FormatManager manager;
  private final String format;

  public FormatManagerTest() {
    MockitoAnnotations.initMocks(this);
    manager = new FormatManager(config);
    format = "%player joined the game";
  }

  @Test
  public void testGetFormat() {
    when(config.getString("default.join")).thenReturn(format);

    assertEquals(format, manager.getFormat("join", null));
  }

  @Test
  public void testGetCustomFormat() {
    when(config.isSet("custom.join.Steve")).thenReturn(true);
    when(config.getString("custom.join.Steve")).thenReturn(format);

    assertEquals(format, manager.getFormat("join", "Steve"));
  }

  @Test
  public void testSetFormat() {
    manager.setFormat("join", format);

    verify(config).set("default.join", format);
  }

  @Test
  public void testSetCustomFormat() {
    manager.setFormat("join", "Steve", format);

    verify(config).set("custom.join.Steve", format);
  }

}
