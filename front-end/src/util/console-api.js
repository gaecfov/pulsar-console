import axios from 'axios';
import adminApi from '@/util/pulsar-api';
import { useGlobalStore } from '@/stroes/useGlobalStore';
import router from '@/router';
import { i18n } from '@/i18n.config';

const { global } = i18n;
const consoleApi = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

consoleApi.interceptors.request.use((config) => {
  const store = useGlobalStore();
  if (store.isLogin) {
    config.headers['Authorization'] = `Bearer ${store.token}`;
  }
  return config;
});
consoleApi.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // 处理响应错误
    if (error.response && error.response.status === 401) {
      router.push('/auth/login');
    } else if (error.response && error.response.status === 403) {
      router.push('/auth/access');
    } else if (error.response && error.response.status === 404) {
      router.push('/404');
    } else {
      const message = error.response ? error.response.data : error.message;
      adminApi.$toast.add({
        severity: 'error',
        summary: global.t('error.message.request-failed'),
        detail: message,
        life: 3000
      });
    }
    return Promise.reject(error);
  }
);
export default consoleApi;
