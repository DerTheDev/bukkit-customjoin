package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ArgumentsContainCheck implements ICommandCheck {

  private final String string;
  private final String message;

  public ArgumentsContainCheck(String string, String message) {
    this.string = string;
    this.message = message;
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    for (String s : args) {
      if (s.contains(string)) {
        return;
      }
    }

    throw new CheckException(message);
  }

}
