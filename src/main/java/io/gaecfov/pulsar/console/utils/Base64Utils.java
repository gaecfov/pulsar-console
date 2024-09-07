package io.gaecfov.pulsar.console.utils;

import java.util.Base64;
import lombok.experimental.UtilityClass;

/**
 * @author zhangqin
 */
@UtilityClass
public class Base64Utils {

  public static String encode(byte[] src) {
    return Base64.getEncoder().encodeToString(src);
  }

  public static byte[] encode(String src) {
    return Base64.getDecoder().decode(src);
  }
}
