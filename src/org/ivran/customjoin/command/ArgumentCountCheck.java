package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ArgumentCountCheck implements ICommandCheck {

  private final int min;
  private final int max;

  public ArgumentCountCheck(int min, int max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    if ((min > 0 && args.length < min) || (max > 0 && args.length > max)) {
      throw new CheckException("Command.SyntaxError");
    }
  }

}
