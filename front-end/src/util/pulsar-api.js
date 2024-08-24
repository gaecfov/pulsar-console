import axios from 'axios';
import { useGlobalStore } from '@/stroes/useGlobalStore';
import router from '@/router';

const adminApi = axios.create({
  baseURL: '/proxy/admin/v2',
  headers: {
    'Content-Type': 'application/json',
    Accept: '*/*'
  }
});
adminApi.interceptors.request.use((config) => {
  const store = useGlobalStore();
  // 在请求头中添加认证令牌
  if (store.isLogin) {
    config.headers['Authorization'] = `Bearer ${store.token}`;
  }
  config.headers['X-PULSAR-INSTANCE-ID'] = store.instanceId;
  return config;
});
adminApi.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      router.push('/auth/login');
    } else if (error.response && error.response.status === 403) {
      router.push('/auth/access');
    } else {
      const message = error.response ? error.response.data : error.message;
      adminApi.$toast.add({
        severity: 'error',
        summary: 'Request Failed',
        detail: message,
        life: 3000
      });
    }
    return Promise.reject(error);
  }
);

export default adminApi;
