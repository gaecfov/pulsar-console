package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.Instance;
import java.io.IOException;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;

/**
 * @author zhangqin
 */
public class PulsarRequestInterceptor implements HttpRequestInterceptor {

  private final Instance instance;

  public PulsarRequestInterceptor(Instance instance) {
    this.instance = instance;
  }

  /**
   * Processes a request. On the client side, this step is performed before the request is sent to
   * the server. On the server side, this step is performed on incoming messages before the message
   * body is evaluated.
   *
   * @param request the request to process
   * @param entity  the request entity details or {@code null} if not available
   * @param context the context for the request
   * @throws HttpException in case of an HTTP protocol violation
   * @throws IOException   in case of an I/O error
   */
  @Override
  public void process(HttpRequest request, EntityDetails entity, HttpContext context)
      throws HttpException, IOException {
    if (instance.isAuthenticationEnabled()) {
      request.setHeader("Authorization", "Bearer " + instance.getAuthenticationToken());
    }
    request.setHeader("Content-Type", "application/json");
    request.setHeader("Accept-Encoding", "gzip, deflate, br");
    request.setHeader("Accept", "*/*");
  }
}
