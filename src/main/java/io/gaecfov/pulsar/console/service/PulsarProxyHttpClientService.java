package io.gaecfov.pulsar.console.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.gaecfov.pulsar.console.entity.Instance;
import io.gaecfov.pulsar.console.event.InstanceModifiedEvent;
import io.gaecfov.pulsar.console.exception.DataNotFoundException;
import io.gaecfov.pulsar.console.exception.InvalidTlsConfigurationException;
import io.gaecfov.pulsar.console.utils.HttpClientUtils;
import io.gaecfov.pulsar.console.utils.TLSUtils;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.springframework.context.ApplicationListener;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Slf4j
@Service
public class PulsarProxyHttpClientService implements ApplicationListener<InstanceModifiedEvent> {

  private final InstanceService instanceService;
  private final Cache<Long, Pair<Instance, CloseableHttpClient>> httpClientCache;

  public PulsarProxyHttpClientService(InstanceService instanceService) {
    this.instanceService = instanceService;
    this.httpClientCache = Caffeine.newBuilder()
        .maximumSize(5)
        .expireAfterWrite(Duration.ofMinutes(5))
        .build();
  }

  public Pair<Instance, CloseableHttpClient> getHttpClient(Long instanceId) {
    return httpClientCache.get(instanceId, k -> {
      Optional<Instance> optional = instanceService.getById(instanceId);
      if (optional.isEmpty()) {
        throw new DataNotFoundException("instance not found");
      }
      Instance instance = optional.get();
      CloseableHttpClient httpClient =
          instance.isTlsEnabled() ? createSSLHttpClient(instance) : createHttpClient(instance);
      return Pair.of(instance, httpClient);
    });
  }

  private @PolyNull CloseableHttpClient createHttpClient(Instance instance) {
    return HttpClients.custom()
        .addRequestInterceptorLast(new PulsarRequestInterceptor(instance))
        .disableAutomaticRetries()
        .build();
  }

  private @PolyNull CloseableHttpClient createSSLHttpClient(Instance instance) {
    try {
      HttpClientBuilder httpClientBuilder;
      if ("pem".equalsIgnoreCase(instance.getTlsType())) {
        httpClientBuilder = createSSLHttpClientWithPEM(instance);
      } else {
        httpClientBuilder = createSSLHttpClientWithJKS(instance);
      }
      return httpClientBuilder
          .setDefaultRequestConfig(
              RequestConfig
                  .custom()
                  .setConnectionRequestTimeout(Timeout.ofSeconds(5))
                  .build())
          .addRequestInterceptorLast(new PulsarRequestInterceptor(instance))
          .disableAutomaticRetries()
          .build();
    } catch (Exception e) {
      throw new InvalidTlsConfigurationException();
    }
  }

  private @PolyNull HttpClientBuilder createSSLHttpClientWithJKS(Instance instance)
      throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
    KeyStore keyStore = TLSUtils.loadKeyStore(instance.getTlsKeyStoreType(),
        instance.getTlsKeyStore(),
        instance.getTlsKeyStorePassword());
    KeyStore trustStore = TLSUtils.loadKeyStore(instance.getTlsTrustStoreType(),
        instance.getTlsTrustStore(),
        instance.getTlsTrustStorePassword());
    return HttpClientUtils.getHttpClientBuildWithTls(keyStore, trustStore);
  }

  private @PolyNull HttpClientBuilder createSSLHttpClientWithPEM(Instance instance)
      throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
    String certText = instance.getTlsCert();
    String keyText = instance.getTlsKey();
    String trustCertText = instance.getTlsTrustCert();
    X509Certificate certificate = TLSUtils.certificateOf(certText);
    PrivateKey privateKey = TLSUtils.privateKeyOf(keyText);

    X509Certificate trustCertificate = TLSUtils.certificateOf(trustCertText);
    KeyStore keyStore = TLSUtils.createKeyStore("JKS", "admin", certificate, privateKey);
    KeyStore trustStore = TLSUtils.createKeyStore("JKS", "ca", trustCertificate);

    return HttpClientUtils.getHttpClientBuildWithTls(keyStore, trustStore);
  }

  /**
   * Handle an application event.
   *
   * @param event the event to respond to
   */
  @Override
  public void onApplicationEvent(InstanceModifiedEvent event) {
    Instance instance = event.getInstance();
    httpClientCache.invalidate(instance.getId());
    log.info("invalidate instance {}'s http client cache", instance.getName());
  }
}
