package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getString;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatCodes;

public abstract class SetMessageBase extends AbstractExecutor {

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    String format = createFormat(sender, cmd, args);
    StringBuilder statusBuilder = new StringBuilder();

    if (format != null) {
      if (!sender.hasPermission("customjoin.colors") && FormatCodes.containsColors(format)) {
        format = FormatCodes.stripColors(format);
        statusBuilder.append(getString("Color.Warning")).append(getString("Command.ColorsRemoved")).append('\n');
      }

      if (!sender.hasPermission("customjoin.formats") && FormatCodes.containsFormats(format)) {
        format = FormatCodes.stripFormats(format);
        statusBuilder.append(getString("Color.Warning")).append(getString("Command.FormatsRemoved")).append('\n');
      }
    }

    return statusBuilder.append(saveFormat(sender, format, args)).toString();
  }

  protected String createFormat(CommandSender sender, Command cmd, String[] args) {
    if (args.length == 0) {
      return null;
    }
    else {
      StringBuilder formatBuilder = new StringBuilder();

      for (String s : args) {
        formatBuilder.append(s).append(' ');
      }

      return formatBuilder.toString().trim();
    }
  }

  protected abstract String saveFormat(CommandSender sender, String format, String[] args);

}
