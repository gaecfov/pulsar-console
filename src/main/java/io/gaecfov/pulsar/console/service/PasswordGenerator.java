package io.gaecfov.pulsar.console.service;

import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

/**
 * @author zhangqin
 */
@UtilityClass
public class PasswordGenerator {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#";

  public static String generatePassword(int length) {
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      password.append(CHARACTERS.charAt(index));
    }

    return password.toString();
  }
}
