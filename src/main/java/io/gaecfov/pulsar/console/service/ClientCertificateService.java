package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.ClientCertificate;
import io.gaecfov.pulsar.console.repository.ClientCertificateRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Service
public class ClientCertificateService {


  private final ClientCertificateRepository clientCertificateRepository;

  public ClientCertificateService(ClientCertificateRepository clientCertificateRepository) {
    this.clientCertificateRepository = clientCertificateRepository;
  }

  public List<ClientCertificate> listAll() {
    return clientCertificateRepository.findAll();
  }

  public ClientCertificate create(ClientCertificate clientCertificate) {
    return clientCertificateRepository.save(clientCertificate);
  }

  public ClientCertificate getById(Long jwtKeyId) {
    return clientCertificateRepository.findById(jwtKeyId).orElse(null);
  }

  public void modify(ClientCertificate clientCertificate) {
    clientCertificateRepository.save(clientCertificate);
  }

  public void remove(Long jwtKeyId){
    clientCertificateRepository.deleteById(jwtKeyId);
  }
}
