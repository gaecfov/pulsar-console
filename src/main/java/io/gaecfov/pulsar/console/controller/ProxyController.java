package io.gaecfov.pulsar.console.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.gaecfov.pulsar.console.entity.Instance;
import io.gaecfov.pulsar.console.service.InstanceService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangqin
 */
@Slf4j
@Controller
public class ProxyController {

  private final RestTemplate restTemplate;
  private final InstanceService instanceService;
  private final Cache<Long, Instance> instanceCache;

  public ProxyController(InstanceService instanceService) {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(5000); // 5秒
    factory.setReadTimeout(5000); // 5秒

    this.restTemplate = new RestTemplate(factory);
    this.instanceService = instanceService;
    this.instanceCache = Caffeine.newBuilder()
        .maximumSize(5)
        .expireAfterWrite(Duration.ofMinutes(5))
        .build();
  }

  @RequestMapping(value = "/proxy/**")
  public ResponseEntity<byte[]> proxyRequest(HttpMethod method, HttpServletRequest request,
      @RequestHeader(value = "X-PULSAR-INSTANCE-ID", defaultValue = "1") Long instanceId,
      @RequestHeader(value = "X-ORIGIN-DOMAIN", required = false) String originDomain,
      @RequestBody(required = false) byte[] body) {
    String url;
    if (originDomain != null && !originDomain.isBlank()) {
      url = "http://" + originDomain;
    } else {
      Instance instance = instanceCache.get(instanceId, key -> {
        Optional<Instance> optional = instanceService.getById(key);
        if (optional.isPresent()) {
          return optional.get();
        }
        throw new IllegalArgumentException("Instance not found");
      });
      url = instance.getWebServiceUrl();
    }
    url += request.getRequestURI().substring("/proxy".length());
    String queryParams = request.getQueryString();
    if (queryParams != null) {
      url += "?" + queryParams;
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Accept-Encoding", "gzip, deflate, br");
    headers.add("Accept", "*/*");

    HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);
    try {
      ResponseEntity<byte[]> response = restTemplate.exchange(url, method, entity, byte[].class);

      return ResponseEntity.status(response.getStatusCode())
          .headers(response.getHeaders())
          .body(response.getBody());
    } catch (HttpClientErrorException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsByteArray());
    }
  }
}