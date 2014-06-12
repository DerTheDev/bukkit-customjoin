package org.ivran.customjoin;

import java.util.regex.Pattern;

public class FormatCodes {

  private static final Pattern colorCodes = Pattern.compile("(?i)&([0-9A-Fa-f])");
  private static final Pattern formatCodes = Pattern.compile("(?i)&([K-Ok-oRr])");

  public static boolean containsColors(String s) {
    return colorCodes.matcher(s).find();
  }

  public static boolean containsFormats(String s) {
    return formatCodes.matcher(s).find();
  }

  public static String stripColors(String s) {
    return colorCodes.matcher(s).replaceAll("");
  }

  public static String stripFormats(String s) {
    return formatCodes.matcher(s).replaceAll("");
  }

  public static String applyColors(String s) {
    return colorCodes.matcher(s).replaceAll("\247$1");
  }

  public static String applyFormats(String s) {
    return formatCodes.matcher(s).replaceAll("\247$1");
  }

  public static String applyAll(String s) {
    return applyColors(applyFormats(s));
  }

}
