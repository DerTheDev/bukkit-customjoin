package test;

import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getColor;
import static org.ivran.customjoin.ResourceHelper.getMessage;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        {"join"},
        {"quit"},
        {"kick"}
    });
  }

  @Mock private FormatManager manager;
  @Mock private CommandSender sender;
  @Mock private Command command;

  private final SetMessageExecutor executor;
  private final String eventName;
  private final String newFormat;

  public SetMessageExecutorTest(String eventName) {
    MockitoAnnotations.initMocks(this);

    executor = new SetMessageExecutor(manager, eventName);
    this.eventName = eventName;
    newFormat = "&k&a%player has entered the server";
  }

  @Test
  public void testWithoutPermission() {
    doThrow(new RuntimeException("Player did something without permission"))
    .when(manager).setFormat(anyString(), anyString());

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));

    verify(sender).sendMessage(getColor("Error") + getMessage("Command.NoPermission"));
  }

  @Test
  public void testSet() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(manager).setFormat(eventName, newFormat);
    verify(sender).sendMessage(getColor("Success") + formatMessage("Command.MessageSet", eventName));
  }

  @Test
  public void testColorPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.colors")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(manager).setFormat(eventName, FormatCodes.stripColors(newFormat));

    String expectedMessage = getColor("Warning") + getMessage("Command.ColorsRemoved") + '\n'
        + getColor("Success") + formatMessage("Command.MessageSet", eventName);

    verify(sender).sendMessage(expectedMessage);
  }

  @Test
  public void testFormatPermissions() {
    when(sender.hasPermission(anyString())).thenReturn(true);
    when(sender.hasPermission("customjoin.formats")).thenReturn(false);

    assertTrue(executor.onCommand(sender, command, "", newFormat.split(" ")));

    verify(manager).setFormat(eventName, FormatCodes.stripFormats(newFormat));

    String expectedMessage = getColor("Warning") + getMessage("Command.FormatsRemoved") + '\n'
        + getColor("Success") + formatMessage("Command.MessageSet", eventName);

    verify(sender).sendMessage(expectedMessage);
  }

  @Test
  public void testReset() {
    when(sender.hasPermission(anyString())).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));

    verify(manager).setFormat(eventName, null);
    verify(sender).sendMessage(getColor("Success") + formatMessage("Command.MessageReset", eventName));
  }

}
