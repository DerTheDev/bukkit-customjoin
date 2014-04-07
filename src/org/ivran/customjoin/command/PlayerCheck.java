package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCheck implements ICommandCheck {

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    if (!(sender instanceof Player)) {
      throw new CheckException("Command.PlayerOnly");
    }
  }

}
