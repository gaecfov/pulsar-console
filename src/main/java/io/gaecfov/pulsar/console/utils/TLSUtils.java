package io.gaecfov.pulsar.console.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import lombok.experimental.UtilityClass;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.boot.ssl.pem.PemContent;

/**
 * @author zhangqin
 */
@UtilityClass
public class TLSUtils {

  private static final char[] PASSWORD = {};

  public static PrivateKey privateKeyOf(String text) {
    PemContent pem = PemContent.of(text);
    return pem.getPrivateKey();
  }

  public static PrivateKey loadPrivateKey(Path path) throws IOException {
    PemContent pem = PemContent.load(path);
    return pem.getPrivateKey();
  }


  public static X509Certificate loadCertificate(Path path) throws IOException {
    PemContent pem = PemContent.load(path);
    List<X509Certificate> certificates = pem.getCertificates();
    return certificates.stream().findFirst().orElse(null);
  }

  public static X509Certificate certificateOf(String text) {
    PemContent pem = PemContent.of(text);
    List<X509Certificate> certificates = pem.getCertificates();
    return certificates.stream().findFirst().orElse(null);
  }

  public static KeyStore loadKeyStore(String type, String text, String password)
      throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
    KeyStore keyStore = KeyStore.getInstance(type);
    keyStore.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)),
        password.toCharArray());
    return keyStore;
  }

  public static KeyStore createKeyStore(String type, String alias, Certificate certificate)
      throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
    return createKeyStore(type, alias, certificate, null);
  }

  public static KeyStore createKeyStore(String type, String alias, Certificate certificate,
      PrivateKey privateKey)
      throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
    KeyStore keyStore = KeyStore.getInstance(type);
    keyStore.load(null, null);
    keyStore.setCertificateEntry(alias, certificate);
    if (privateKey != null) {
      keyStore.setKeyEntry(alias, privateKey, PASSWORD,
          new Certificate[]{certificate});
    }
    return keyStore;
  }

  public static SSLContext crateSSLContext(KeyStore keyStore, KeyStore trustStore)
      throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    return SSLContexts.custom()
        .loadKeyMaterial(keyStore, PASSWORD)
        .loadTrustMaterial(trustStore, TrustAllStrategy.INSTANCE)
        .build();

  }
}
