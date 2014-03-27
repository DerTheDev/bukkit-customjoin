package org.ivran.customjoin.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.Strings;

public abstract class AbstractExecutor implements CommandExecutor {

  private final Strings strings;
  private List<ICommandCheck> commandChecks;

  public AbstractExecutor(CustomJoinPlugin plugin, ICommandCheck... commandChecks) {
    this.strings = plugin.getStrings();
    this.commandChecks = new ArrayList<ICommandCheck>();
    for (ICommandCheck c : commandChecks) {
      this.commandChecks.add(c);
    }
  }

  /**
   * Called before {@code execute}. Any and all permission cheking/argument
   * validation should be done here.
   * 
   * @return {@code null} if everything is fine, an error message if the command
   *         cannot/should not be executed.
   */
  protected abstract String getError(CommandSender sender, Command cmd, String[] args);

  /**
   * Called after {@code getError} has ensured that the command can be executed.
   */
  protected abstract void execute(CommandSender sender, Command cmd, String[] args);

  @Override
  public final boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    try {
      for (ICommandCheck c : commandChecks) {
        c.doCheck(sender, cmd, label, args);
      }

      execute(sender, cmd, args);
    }
    catch (CheckException e) {
      sender.sendMessage(strings.get("Color.Error") + strings.get(e.getMessage()));
    }

    return true;
  }
}
