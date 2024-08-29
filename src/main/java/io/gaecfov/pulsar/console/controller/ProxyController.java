package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.Instance;
import io.gaecfov.pulsar.console.service.HttpClientService;
import io.gaecfov.pulsar.console.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangqin
 */
@Slf4j
@Controller
public class ProxyController {

  private final HttpClientService httpClientService;

  public ProxyController(HttpClientService httpClientService) {
    this.httpClientService = httpClientService;
  }

  @RequestMapping(value = "/proxy/**")
  public ResponseEntity<byte[]> proxyRequest(HttpMethod method, HttpServletRequest request,
      @RequestHeader(value = "X-PULSAR-INSTANCE-ID", defaultValue = "1") Long instanceId,
      @RequestHeader(value = "X-ORIGIN-DOMAIN", required = false) String originDomain,
      @RequestBody(required = false) byte[] body) {

    Pair<Instance, CloseableHttpClient> httpClientPair = httpClientService.getHttpClient(
        instanceId);
    Instance instance = httpClientPair.getFirst();
    CloseableHttpClient httpClient = httpClientPair.getSecond();

    String url = StringUtils.concat(
        StringUtils.isBlank(originDomain) ? instance.getWebServiceUrl() : originDomain,
        request.getRequestURI().substring("/proxy".length()));
    String queryParams = request.getQueryString();
    if (queryParams != null) {
      url += "?" + queryParams;
    }
    ClassicHttpRequest httpRequest = createHttpRequest(method, url, body);
    try {
      return httpClient.execute(httpRequest, res -> {
        byte[] byteArray = EntityUtils.toByteArray(res.getEntity());
        BodyBuilder bodyBuilder = ResponseEntity.status(res.getCode());
        for (Header header : res.getHeaders()) {
          bodyBuilder.header(header.getName(), header.getValue());
        }
        return bodyBuilder.body(byteArray);
      });
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Requst Failed".getBytes(StandardCharsets.UTF_8));
    }
  }

  private ClassicHttpRequest createHttpRequest(HttpMethod method, String url, byte[] body) {
    HttpUriRequestBase request = new HttpUriRequestBase(method.name(), URI.create(url));
    if (body != null && body.length > 0) {
      request.setEntity(new ByteArrayEntity(body, ContentType.APPLICATION_JSON));
    }
    return request;
  }
}