package test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.FormatManager;
import org.ivran.customjoin.command.SetMyMessageExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(Parameterized.class)
public class SetMyMessageExecutorTest {

  @Parameters
  public static Collection<Object[]> params() {
    return Arrays.asList(new Object[][] {
        {"join"},
        {"quit"}
    });
  }

  @Mock private FileConfiguration config;
  @Mock private FormatManager manager;
  @Mock private Command command;

  private final CommandSender sender;
  private final SetMyMessageExecutor executor;
  private final String eventName;
  private final String newFormat;

  public SetMyMessageExecutorTest(String eventName) {
    MockitoAnnotations.initMocks(this);

    sender = mock(Player.class);
    when(sender.getName()).thenReturn("Steve");

    executor = new SetMyMessageExecutor(config, manager, eventName);
    this.eventName = eventName;
    newFormat = "&k&a%player has entered the server";
  }

  @Test
  public void testWithoutPermission() {
    doThrow(new RuntimeException("Player did something without permission"))
    .when(manager).setFormat(anyString(), anyString(), anyString());

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));
  }

  @Test
  public void testWithoutPlayerName() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(config.getBoolean("require-player-name.setmy")).thenReturn(true);

    final SetMyMessageExecutor executor = new SetMyMessageExecutor(config, manager, eventName);

    doThrow(new RuntimeException("%player was not in the format, manager.setFormat called anyway"))
    .when(manager).setFormat(anyString(), anyString());

    final String format = "This format does not contain a player name token!";

    assertTrue(executor.onCommand(sender, command, "", format.split(" ")));
  }

  @Test
  public void testSet() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(manager).setFormat(eventName, sender.getName(), newFormat);
  }

  @Test
  public void testColorPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.colors")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(manager).setFormat(eventName, sender.getName(), FormatCodes.stripColors(newFormat));
  }

  @Test
  public void testFormatPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.formats")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(manager).setFormat(eventName, sender.getName(), FormatCodes.stripFormats(newFormat));
  }

  @Test
  public void testReset() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));

    verify(manager).setFormat(eventName, sender.getName(), null);
  }

}
