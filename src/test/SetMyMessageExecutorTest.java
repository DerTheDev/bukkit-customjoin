package test;

import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getMessage;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.ivran.customjoin.FormatCodes;
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
        {"Join"},
        {"Quit"}
    });
  }

  @Mock private FileConfiguration config;
  @Mock private Command command;

  private final CommandSender sender;
  private final SetMyMessageExecutor executor;
  private final String configNode;
  private final String eventName;
  private final String newFormat;

  public SetMyMessageExecutorTest(String eventName) {
    MockitoAnnotations.initMocks(this);

    sender = mock(Player.class);
    when(sender.getName()).thenReturn("Steve");

    executor = new SetMyMessageExecutor(config, eventName);
    this.configNode = String.format("custom.%s.%s", eventName.toLowerCase(), sender.getName());
    this.eventName = eventName;
    newFormat = "&k&a%player has entered the server";
  }

  @Test
  public void testWithoutPermission() {
    doThrow(new RuntimeException("Player did something without permission"))
    .when(config).set(eq(configNode), anyString());

    assertFalse(executor.onCommand(sender, command, "", new String[] {}));

    verify(sender).sendMessage(ChatColor.RED + getMessage("Command.NoPermission"));
  }

  @Test
  public void testSet() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(config).set(configNode, newFormat);
    verify(sender).sendMessage(formatMessage("Command.OwnMessageSet", eventName));
  }

  @Test
  public void testColorPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.colors")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(config).set(configNode, FormatCodes.stripColors(newFormat));

    String expectedMessage = ChatColor.YELLOW + getMessage("Command.ColorsRemoved") + '\n'
        + formatMessage("Command.OwnMessageSet", eventName);

    verify(sender).sendMessage(expectedMessage);
  }

  @Test
  public void testFormatPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.formats")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(config).set(configNode, FormatCodes.stripFormats(newFormat));

    String expectedMessage = ChatColor.YELLOW + getMessage("Command.FormatsRemoved") + '\n'
        + formatMessage("Command.OwnMessageSet", eventName);

    verify(sender).sendMessage(expectedMessage);
  }

  @Test
  public void testReset() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));

    verify(config).set(configNode, null);
    verify(sender).sendMessage(formatMessage("Command.OwnMessageReset", eventName));
  }

}
