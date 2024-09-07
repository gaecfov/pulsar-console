package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.ClientToken;
import io.gaecfov.pulsar.console.repository.ClientTokenRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Service
public class ClientTokenService {


  private final ClientTokenRepository clientTokenRepository;

  public ClientTokenService(ClientTokenRepository clientTokenRepository) {
    this.clientTokenRepository = clientTokenRepository;
  }

  public List<ClientToken> listAll() {
    return clientTokenRepository.findAll();
  }

  public ClientToken create(ClientToken clientToken) {
    return clientTokenRepository.save(clientToken);
  }

  public ClientToken getById(Long jwtTokenId) {
    return clientTokenRepository.findById(jwtTokenId).orElse(null);
  }

  public void modify(ClientToken clientToken) {
    clientTokenRepository.save(clientToken);
  }

  public void remove(Long jwtTokenId){
    clientTokenRepository.deleteById(jwtTokenId);
  }
}
