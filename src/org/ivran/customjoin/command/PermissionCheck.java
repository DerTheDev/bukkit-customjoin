package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PermissionCheck implements ICommandCheck {

  private final String permission;

  public PermissionCheck(String permission) {
    this.permission = permission;
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    if (!sender.hasPermission(permission)) {
      throw new CheckException(getMessage("Command.NoPermission"));
    }
  }

}
