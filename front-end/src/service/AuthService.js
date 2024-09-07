import consoleApi from '@/util/console-api';

export const login = (loginRequest) => {
  return consoleApi.post('/login', loginRequest);
};

export const getCurrentUser = () => {
  return consoleApi.get('/currentUser');
};
