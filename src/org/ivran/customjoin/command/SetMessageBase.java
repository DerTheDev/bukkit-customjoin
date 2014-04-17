package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getColor;
import static org.ivran.customjoin.ResourceHelper.getMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.MessageFormatter;

public abstract class SetMessageBase extends AbstractExecutor {

  private final MessageFormatter formatter;

  public SetMessageBase() {
    formatter = new MessageFormatter();
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    String format = createFormat(sender, cmd, args);
    StringBuilder statusBuilder = new StringBuilder();

    if (format != null) {
      if (!sender.hasPermission("customjoin.colors") && FormatCodes.containsColors(format)) {
        format = FormatCodes.stripColors(format);
        statusBuilder
        .append(getColor("Warning"))
        .append(getMessage("Command.ColorsRemoved"))
        .append('\n');
      }

      if (!sender.hasPermission("customjoin.formats") && FormatCodes.containsFormats(format)) {
        format = FormatCodes.stripFormats(format);
        statusBuilder
        .append(getColor("Warning"))
        .append(getMessage("Command.FormatsRemoved"))
        .append('\n');
      }
    }

    statusBuilder
    .append(saveFormat(sender, format, args))
    .append('\n')
    .append(getColor("Default"))
    .append(getMessage("Command.MessagePreview"))
    .append(ChatColor.RESET)
    .append(formatter.format(format, "Steve"));

    return statusBuilder.toString();
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
