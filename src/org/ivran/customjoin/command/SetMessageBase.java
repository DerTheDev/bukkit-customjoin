package org.ivran.customjoin.command;

import static org.ivran.customjoin.ResourceHelper.getColor;
import static org.ivran.customjoin.ResourceHelper.getMessage;
import static org.ivran.customjoin.ResourceHelper.formatMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.MessageFormatter;

public abstract class SetMessageBase extends AbstractExecutor {

  private final FileConfiguration config;
  private final MessageFormatter formatter;

  public SetMessageBase(FileConfiguration config) {
    this.config = config;
    formatter = new MessageFormatter();
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) throws CheckException {
    if (args.length == 0) {
      throw new CheckException(getMessage("Command.SyntaxError"));
    }

    String format = createFormat(sender, cmd, args);

    int limit = config.getInt("message-limit");
    if (limit > 0 && format.length() > limit) {
      throw new CheckException(formatMessage("Command.MessageTooLong", limit));
    }

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

    statusBuilder.append(saveFormat(sender, format, args));

    if (format != null) {
      showPreview(format, statusBuilder);
    }

    return statusBuilder.toString();
  }

  private void showPreview(String format, StringBuilder statusBuilder) {
    statusBuilder
    .append('\n')
    .append(getColor("Default"))
    .append(getMessage("Command.MessagePreview"))
    .append(ChatColor.RESET)
    .append(' ')
    .append(formatter.format(format, "Steve"));
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
