package tests;

import static org.ivran.customjoin.ResourceHelper.formatString;
import static org.ivran.customjoin.ResourceHelper.getString;
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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.ivran.customjoin.command.SetPlayerMessageExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(Parameterized.class)
public class SetPlayerMessageExecutorTest {

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
  private final SetPlayerMessageExecutor executor;
  private final String configNode;
  private final String eventName;
  private final String newFormat;

  public SetPlayerMessageExecutorTest(String eventName) {
    MockitoAnnotations.initMocks(this);

    sender = mock(Player.class);
    when(sender.getName()).thenReturn("Steve");

    executor = new SetPlayerMessageExecutor(config, eventName);
    this.configNode = String.format("custom.%s.%s", eventName.toLowerCase(), "John");
    this.eventName = eventName;
    newFormat = "&a%player has entered the server";
  }

  @Test
  public void testWithoutPermission() {
    doThrow(new RuntimeException("Player did something without permission"))
    .when(config).set(eq(configNode), anyString());

    assertFalse(executor.onCommand(sender, command, "", new String[] {}));

    verify(sender).sendMessage("§c" + getString("Command.NoPermission"));
  }

  @Test
  public void testSet() {
    when(sender.hasPermission("customjoin.set")).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", ("John " + newFormat).split(" ")));

    verify(config).set(configNode, newFormat);
    verify(sender).sendMessage(formatString("Command.PlayerMessageSet", "John", eventName));
  }

  @Test
  public void testReset() {
    when(sender.hasPermission("customjoin.set")).thenReturn(true);

    assertTrue(executor.onCommand(sender, command, "", new String[] {"John"}));

    verify(config).set(configNode, null);
    verify(sender).sendMessage(formatString("Command.PlayerMessageReset", "John", eventName));
  }

}
