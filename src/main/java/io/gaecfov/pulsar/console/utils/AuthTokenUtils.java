package io.gaecfov.pulsar.console.utils;

import io.gaecfov.pulsar.console.exception.SystemRuntimeException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthTokenUtils {

  @SuppressWarnings("unchecked")
  public static <T> T forKey(String id) {
    return (T) SIG.get().forKey(id);
  }

  public static SecretKey createSecretKey(String algorithm) {
    MacAlgorithm macAlgorithm = forKey(algorithm);
    return createSecretKey(macAlgorithm);
  }

  public static SecretKey createSecretKey(MacAlgorithm algorithm) {
    return algorithm.key().build();
  }

  public static KeyPair createKeyPair(String algorithm) {
    SignatureAlgorithm signatureAlgorithm = forKey(algorithm);
    return createKeyPair(signatureAlgorithm);
  }

  public static KeyPair createKeyPair(SignatureAlgorithm algorithm) {
    return algorithm.keyPair().build();
  }

  public static Key decodeSecretKey(byte[] secretKey) {
    return Keys.hmacShaKeyFor(secretKey);
  }


  public static PrivateKey decodePrivateKey(byte[] key, String algorithm) {
    try {
      KeyPair keyPair = createKeyPair(algorithm);
      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
      KeyFactory kf = KeyFactory.getInstance(keyPair.getPrivate().getAlgorithm());
      return kf.generatePrivate(spec);
    } catch (Exception e) {
      throw new SystemRuntimeException(e);
    }
  }

  public static PublicKey decodePublicKey(byte[] key, String algorithm) {
    try {
      KeyPair keyPair = createKeyPair(algorithm);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(key);
      KeyFactory kf = KeyFactory.getInstance(keyPair.getPublic().getAlgorithm());
      return kf.generatePublic(spec);
    } catch (Exception e) {
      throw new SystemRuntimeException(e);
    }
  }

  public static String createToken(String subject, Key key, Date expiration) {
    return Jwts.builder()
      .subject(subject)
      .signWith(key)
      .expiration(expiration)
      .compact();
  }
}
