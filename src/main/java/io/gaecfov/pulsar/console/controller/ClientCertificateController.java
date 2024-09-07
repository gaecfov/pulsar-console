package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.ClientCertificate;
import io.gaecfov.pulsar.console.request.ClientCertificateCreateRequest;
import io.gaecfov.pulsar.console.response.SimpleClientCertificate;
import io.gaecfov.pulsar.console.service.ClientCertificateService;
import io.gaecfov.pulsar.console.utils.AuthTokenUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/client-certificate")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ClientCertificateController {

  private final ClientCertificateService clientCertificateService;

  @PostMapping
  public void createKey(@RequestBody ClientCertificateCreateRequest request) {
    ClientCertificate clientCertificate = new ClientCertificate();
    clientCertificate.setName(request.getName());
    clientCertificate.setDescription(request.getDescription());
    clientCertificate.setCertificateType(request.getCertificateType());
    clientCertificate.setAlgorithm(request.getAlgorithm());
    if (request.getCertificateType().equalsIgnoreCase("SECRET")) {
      SecretKey secretKey = AuthTokenUtils.createSecretKey(request.getAlgorithm());
      clientCertificate.setSecretKey(secretKey.getEncoded());
    } else {
      KeyPair keyPair = AuthTokenUtils.createKeyPair(request.getAlgorithm());
      clientCertificate.setPublicKey(keyPair.getPublic().getEncoded());
      clientCertificate.setPrivateKey(keyPair.getPrivate().getEncoded());
    }
    clientCertificateService.create(clientCertificate);
  }

  @GetMapping
  public List<SimpleClientCertificate> listClientCertificates() {
    List<ClientCertificate> certificates = clientCertificateService.listAll();
    if (certificates != null) {
      return certificates.stream().map(x -> {
        SimpleClientCertificate certificate = new SimpleClientCertificate();
        certificate.setId(x.getId());
        certificate.setName(x.getName());
        certificate.setDescription(x.getDescription());
        certificate.setCertificateType(x.getCertificateType());
        certificate.setAlgorithm(x.getAlgorithm());
        certificate.setCreatedAt(x.getCreatedAt());
        return certificate;
      }).toList();
    }
    return Collections.emptyList();
  }

  @DeleteMapping("/{clientCertificateId}")
  public void deleteClientCertificate(
    @PathVariable("clientCertificateId") Long clientCertificateId) {
    clientCertificateService.remove(clientCertificateId);
  }

  @GetMapping("/{clientCertificateId}")
  public ClientCertificate getClientCertificate(
    @PathVariable("clientCertificateId") Long clientCertificateId) {
    return clientCertificateService.getById(clientCertificateId);
  }

  @GetMapping("/{clientCertificateId}/download")
  public ResponseEntity<InputStreamResource> downloadCertificate(
    @PathVariable("clientCertificateId") Long clientCertificateId) throws IOException {
    ClientCertificate certificate = clientCertificateService.getById(clientCertificateId);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    if (certificate.getCertificateType().equalsIgnoreCase("SECRET")) {
      byteArrayOutputStream.write(certificate.getSecretKey());
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
        byteArrayOutputStream.toByteArray());
      InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

      HttpHeaders headers = new HttpHeaders();
      String downloadName = certificate.getName() + "-secret.key";
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadName);
      headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

      return ResponseEntity.ok()
        .headers(headers)
        .contentLength(byteArrayOutputStream.size())
        .body(resource);
    } else {
      ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

      ZipEntry publicKeyEntry = new ZipEntry("public.key");
      zipOutputStream.putNextEntry(publicKeyEntry);
      zipOutputStream.write(certificate.getPublicKey());
      zipOutputStream.closeEntry();

      ZipEntry privateKeyEntry = new ZipEntry("private.key");
      zipOutputStream.putNextEntry(privateKeyEntry);
      zipOutputStream.write(certificate.getPrivateKey());
      zipOutputStream.closeEntry();

      zipOutputStream.finish();
      zipOutputStream.close();

      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
        byteArrayOutputStream.toByteArray());
      InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

      HttpHeaders headers = new HttpHeaders();
      String downloadName = certificate.getName() + "-certificate.zip";
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadName);
      headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

      return ResponseEntity.ok()
        .headers(headers)
        .contentLength(byteArrayOutputStream.size())
        .body(resource);
    }
  }
}
