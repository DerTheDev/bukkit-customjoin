package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.formatString;
import static org.ivran.customjoin.ResourceHelper.getString;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SetPlayerMsgExecutor extends SetMessageBaseExecutor {

  private final FileConfiguration config;

  public SetPlayerMsgExecutor(FileConfiguration config) {
    super(1);

    this.config = config;

    addCheck(new PermissionCheck("customjoin.setplayermessage"));
    addCheck(new ArgumentCountCheck(2, -1));
  }


  @Override
  protected String createFormat(CommandSender sender, Command cmd, String[] args) {
    if (args.length - 2 > 0) {
      StringBuilder messageBuilder = new StringBuilder();

      for (int i = 2; i < args.length; i++) {
        messageBuilder.append(args[i]).append(' ');
      }

      return messageBuilder.toString().trim();
    }
    else {
      return null;
    }
  }

  @Override
  protected String saveFormat(String format, String[] args) {
    String player = args[0];
    String type = args[1];

    config.set(String.format("custom.%s.%s", type, player), format);

    if (format == null) {
      return getString("Color.Success") + formatString("Command.PlayerMessageDeleted", player, type);
    }
    else {
      return getString("Color.Success") + formatString("Command.PlayerMessageSet", player, type);
    }
  }
}
