package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.CustomJoinPlugin;
import org.ivran.customjoin.R;

public class SetPlayerMsgExecutor extends SetMessageBaseExecutor {

  private final FileConfiguration config;

  public SetPlayerMsgExecutor(CustomJoinPlugin plugin) {
    super(1);

    addCheck(new PermissionCheck("customjoin.setplayermessage"));
    addCheck(new ArgumentCountCheck(2, -1));

    this.config = plugin.getConfig();
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
      return R.get("Color.Success") + R.format("Command.PlayerMessageDeleted", player, type);
    }
    else {
      return R.get("Color.Success") + R.format("Command.PlayerMessageSet", player, type);
    }
  }
}
