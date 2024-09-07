import consoleApi from '@/util/console-api';

export const listClientTokens = () => {
  return consoleApi.get('/client-token');
};

export const getClientToken = (id) => {
  return consoleApi.get(`/client-token/${id}`);
};

export const createClientToken = (clientToken) => {
  return consoleApi.post('/client-token', clientToken);
};

export const modifyClientToken = (id, clientToken) => {
  return consoleApi.put(`/client-token/${id}`, clientToken);
};

export const deleteClientToken = (id) => {
  return consoleApi.delete(`/client-token/${id}`);
};
