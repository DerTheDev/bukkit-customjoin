package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommandCheck {

  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException;

}
