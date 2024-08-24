import consoleApi from '@/util/console-api';

export const listInstances = () => {
  return consoleApi.get('/instance');
};

export const createInstance = (instance) => {
  return consoleApi.post('/instance', instance);
};

export const modifyInstance = (id, instance) => {
  return consoleApi.put(`/instance/${id}`, instance);
};

export const deleteInstance = (id) => {
  return consoleApi.delete(`/instance/${id}`);
};
