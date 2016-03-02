package com.uml2Java.client.uml2javaUtils;

/**
 * Created by Cristi on 2/4/2016.
 */
public class TextUtil {
  /**
   * Check if the received value is empty or not. (If value is null, we assume that is an empty string)
   * @param value - String
   * @return true - if value is null or empty
   *         false - otherwise
   */
  public static boolean isEmptyString(String value) {
    if (value == null)
      return true;
    value = value.trim();
    return value.isEmpty();
  }

  public static String getFileExtentsion(String fileName) {
    StringBuilder builder = new StringBuilder(fileName);
    int index = builder.lastIndexOf(".");
    if (index == -1)
      return "";
    return builder.substring(index);
  }

  public static String getFileSubpath(String filePath) {
    StringBuilder builder = new StringBuilder(filePath);
    int index = builder.indexOf("elearning\\app_files");
    if (index == -1)
      return null;
    return builder.substring(index);
  }

}
