package io.gaecfov.pulsar.console.utils;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.SSLContext;
import lombok.experimental.UtilityClass;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.util.Timeout;

/**
 * @author zhangqin
 */
@UtilityClass
public class HttpClientUtils {

  public static CloseableHttpClient getHttpClientWithTls(KeyStore keyStore, KeyStore trustStore)
      throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    return getHttpClientBuildWithTls(keyStore, trustStore).build();
  }

  public static HttpClientBuilder getHttpClientBuildWithTls(KeyStore keyStore, KeyStore trustStore)
      throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    SSLContext sslContext = TLSUtils.crateSSLContext(keyStore, trustStore);
    SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
        .setSslContext(sslContext)
        .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
        .build();
    PoolingHttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
        .setSSLSocketFactory(sslSocketFactory)
        .setDefaultConnectionConfig(ConnectionConfig.custom()
            .setConnectTimeout(Timeout.ofSeconds(30))
            .setSocketTimeout(Timeout.ofSeconds(30))
            .build())
        .setDefaultTlsConfig(TlsConfig.custom()
            .setHandshakeTimeout(Timeout.ofSeconds(30))
            .setSupportedProtocols(TLS.V_1_3, TLS.V_1_2)
            .build())
        .build();

    return HttpClients.custom()
        .setConnectionManager(cm);
  }
}

