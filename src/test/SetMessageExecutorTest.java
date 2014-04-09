package test;

import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getMessage;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.FormatManager;
import org.ivran.customjoin.command.SetMessageExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(Parameterized.class)
public class SetMessageExecutorTest {

  @Parameters
  public static Collection<Object[]> params() {
    return Arrays.asList(new Object[][] {
        {"Join"},
        {"Quit"},
        {"Kick"}
    });
  }

  @Mock private FileConfiguration config;
  @Mock private CommandSender sender;
  @Mock private Command command;

  private final FormatManager manager;
  private final SetMessageExecutor executor;
  private final String eventName;
  private final String newFormat;

  public SetMessageExecutorTest(String eventName) {
    MockitoAnnotations.initMocks(this);

    manager = new FormatManager(config);
    executor = new SetMessageExecutor(manager, eventName);
    this.eventName = eventName;
    newFormat = "&k&a%player has entered the server";
  }

  @Test
  public void testWithoutPermission() {
    doThrow(new RuntimeException("Player did something without permission"))
    .when(config).set(anyString(), anyString());

    assertFalse(executor.onCommand(sender, command, "", new String[] {}));

    verify(sender).sendMessage(ChatColor.RED + getMessage("Command.NoPermission"));
  }

  @Test
  public void testSet() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(config).set("format." + eventName, newFormat);
    verify(sender).sendMessage(formatMessage("Command.MessageSet", eventName));
  }

  @Test
  public void testColorPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.colors")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(config).set("format." + eventName, FormatCodes.stripColors(newFormat));

    String expectedMessage = ChatColor.YELLOW + getMessage("Command.ColorsRemoved") + '\n'
        + formatMessage("Command.MessageSet", eventName);

    verify(sender).sendMessage(expectedMessage);
  }

  @Test
  public void testFormatPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.formats")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(config).set("format." + eventName, FormatCodes.stripFormats(newFormat));

    String expectedMessage = ChatColor.YELLOW + getMessage("Command.FormatsRemoved") + '\n'
        + formatMessage("Command.MessageSet", eventName);

    verify(sender).sendMessage(expectedMessage);
  }

  @Test
  public void testReset() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));

    verify(config).set("format." + eventName, null);
    verify(sender).sendMessage(formatMessage("Command.MessageReset", eventName));
  }

}
