package test;

import static org.ivran.customjoin.ResourceHelper.formatMessage;
import static org.ivran.customjoin.ResourceHelper.getColor;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.command.CustomJoinExecutor;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomJoinExecutorTest {

  @Mock private CommandSender sender;
  @Mock private Command command;

  public CustomJoinExecutorTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test() {
    final String name = "CustomJoin";
    final String version = "1.0";
    final String expectedMessage = FormatCodes.applyAll(
        getColor("Default") + formatMessage("Plugin.Info", name, version));
    final PluginDescriptionFile pdf = new PluginDescriptionFile(name, version, null);
    final CustomJoinExecutor executor = new CustomJoinExecutor(pdf);

    assertTrue(executor.onCommand(sender, command, "", new String[] {}));

    verify(sender).sendMessage(expectedMessage);
  }

}
