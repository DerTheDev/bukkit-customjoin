package org.ivran.customjoin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.ivran.customjoin.FormatCodes;
import org.ivran.customjoin.MessageType;
import org.ivran.customjoin.R;

public abstract class SetMessageBaseExecutor extends AbstractExecutor implements ICommandCheck {

  private final int typeIndex;

  public SetMessageBaseExecutor(int typeIndex) {
    super();

    addCheck(this);

    this.typeIndex = typeIndex;
  }

  @Override
  public void doCheck(CommandSender sender, Command cmd, String alias, String[] args) throws CheckException {
    if (args.length <= 0) {
      return;
    }

    try {
      MessageType.valueOf(args[typeIndex].toUpperCase());
    }
    catch (IllegalArgumentException e) {
      throw new CheckException(R.format("Command.UnknownMessageType", args[typeIndex]));
    }
  }

  @Override
  protected String execute(CommandSender sender, Command cmd, String[] args) {
    String format = createFormat(sender, cmd, args);
    StringBuilder statusBuilder = new StringBuilder();

    if (format != null) {
      if (!sender.hasPermission("customjoin.colors") && FormatCodes.containsColors(format)) {
        format = FormatCodes.stripColors(format);
        statusBuilder.append(R.get("Color.Warning")).append(R.get("Command.MessageSet.ColorsRemoved")).append('\n');
      }

      if (!sender.hasPermission("customjoin.formats") && FormatCodes.containsFormats(format)) {
        format = FormatCodes.stripFormats(format);
        statusBuilder.append(R.get("Color.Warning")).append(R.get("Command.MessageSet.FormatsRemoved")).append('\n');
      }
    }

    statusBuilder.append(saveFormat(format, args));
    return statusBuilder.toString();
  }

  protected abstract String createFormat(CommandSender sender, Command cmd, String[] args);

  protected abstract String saveFormat(String format, String[] args);

}
