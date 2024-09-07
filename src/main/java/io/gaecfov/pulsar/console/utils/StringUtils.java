package io.gaecfov.pulsar.console.utils;

import lombok.experimental.UtilityClass;

/**
 * @author zhangqin
 */
@UtilityClass
public class StringUtils {

  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  public static String concat(String... values) {
    StringBuilder sb = new StringBuilder();
    for (String value : values) {
      if (value != null) {
        sb.append(value);
      }
    }
    return sb.toString();
  }
}