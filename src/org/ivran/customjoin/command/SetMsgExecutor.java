package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.R;

public class SetMsgExecutor extends SetMessageBaseExecutor {

  private final FileConfiguration config;

  public SetMsgExecutor(FileConfiguration config) {
    super(0);

    this.config = config;

    addCheck(new PermissionCheck("customjoin.set"));
    addCheck(new ArgumentCountCheck(1, -1));
  }

  @Override
  protected String createFormat(CommandSender sender, Command cmd, String[] args) {
    StringBuilder messageBuilder = new StringBuilder();

    for (int i = 1; i < args.length; i++) {
      messageBuilder.append(args[i]).append(' ');
    }

    return messageBuilder.toString().trim();
  }

  @Override
  protected String saveFormat(String format, String[] args) {
    config.set(String.format("format.%s", args[0]), format);
    return R.get("Color.Success") + R.format("Command.MessageSet", args[0]);
  }
}
