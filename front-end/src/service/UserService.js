import consoleApi from '@/util/console-api';

export const listUsers = () => {
  return consoleApi.get('/user');
};

export const createUser = (user) => {
  return consoleApi.post('/user', user);
};

export const modifyUser = (id, user) => {
  return consoleApi.put(`/user/${id}`, user);
};

export const deleteUser = (id) => {
  return consoleApi.delete(`/user/${id}`);
};

export const changePassword = (id, password) => {
  return consoleApi.put(`/user/${id}/password`, password);
};
