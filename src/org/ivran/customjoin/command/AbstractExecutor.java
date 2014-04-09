package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getColor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatCodes;

public abstract class AbstractExecutor implements CommandExecutor {

  private final List<ICommandCheck> commandChecks;

  public AbstractExecutor() {
    this.commandChecks = new ArrayList<ICommandCheck>();
  }

  public void addCheck(ICommandCheck c) {
    commandChecks.add(c);
  }

  /**
   * Called after it was ensured that the command can be executed.
   * @return A status message for the player.
   */
  protected abstract String execute(CommandSender sender, Command cmd, String[] args);

  @Override
  public final boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    try {
      for (ICommandCheck c : commandChecks) {
        c.doCheck(sender, cmd, label, args);
      }

      sender.sendMessage(FormatCodes.applyAll(execute(sender, cmd, args)));
    }
    catch (CheckException e) {
      String message = getColor("Error") + e.getMessage();
      sender.sendMessage(FormatCodes.applyAll(message));
    }
    return true;
  }
}
