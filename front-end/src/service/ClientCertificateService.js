import consoleApi from '@/util/console-api';

export const listClientCertificates = () => {
  return consoleApi.get('/client-certificate');
};

export const getClientCertificate = (id) => {
  return consoleApi.get(`/client-certificate/${id}`);
};

export const createClientCertificate = (clientCertificate) => {
  return consoleApi.post('/client-certificate', clientCertificate);
};

export const modifyClientCertificate = (id, clientCertificate) => {
  return consoleApi.put(`/client-certificate/${id}`, clientCertificate);
};

export const deleteClientCertificate = (id) => {
  return consoleApi.delete(`/client-certificate/${id}`);
};

export const download = (id) => {
  return consoleApi.get(`/client-certificate/${id}/download`, {
    responseType: 'blob'
  });
};
