package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.ClientCertificate;
import io.gaecfov.pulsar.console.entity.ClientToken;
import io.gaecfov.pulsar.console.request.ClientTokenCreateRequest;
import io.gaecfov.pulsar.console.service.ClientCertificateService;
import io.gaecfov.pulsar.console.service.ClientTokenService;
import io.gaecfov.pulsar.console.utils.AuthTokenUtils;
import java.security.Key;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqin
 */
@RestController
@RequestMapping("/client-token")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ClientTokenController {

  private final ClientCertificateService clientCertificateService;
  private final ClientTokenService clientTokenService;


  @PostMapping
  public void createKey(@RequestBody ClientTokenCreateRequest request) {
    ClientToken clientToken = new ClientToken();
    clientToken.setName(request.getName());
    clientToken.setDescription(request.getDescription());
    clientToken.setRole(request.getRole());
    clientToken.setExpiration(request.getExpiration());
    ClientCertificate clientCertificate = clientCertificateService.getById(
      request.getClientCertificateId());
    Key key;
    if (clientCertificate.getCertificateType().equalsIgnoreCase("SECRET")) {
      key = AuthTokenUtils.decodeSecretKey(clientCertificate.getSecretKey());
    } else {
      key = AuthTokenUtils.decodePrivateKey(clientCertificate.getPrivateKey(),
        clientCertificate.getAlgorithm());
    }
    String token = AuthTokenUtils.createToken(request.getRole(), key, request.getExpiration());
    clientToken.setToken(token);
    clientTokenService.create(clientToken);
  }

  @GetMapping
  public List<ClientToken> listClientTokens() {
    return clientTokenService.listAll();
  }

  @DeleteMapping("/{clientTokenId}")
  public void deleteJwtToken(@PathVariable("clientTokenId") Long clientTokenId) {
    clientTokenService.remove(clientTokenId);
  }

  @GetMapping("/{clientTokenId}")
  public ClientToken getClientToken(@PathVariable("clientTokenId") Long clientTokenId) {
    return clientTokenService.getById(clientTokenId);
  }
}
