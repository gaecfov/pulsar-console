import axios from 'axios';
import { useGlobalStore } from '@/stroes/useGlobalStore';
import { i18n } from '@/i18n.config';
import toastUtil from '@/util/toast-util';
import { useDebounceFn } from '@vueuse/core';

const { global } = i18n;

const debouncedError = useDebounceFn(message =>
  toastUtil.error(message), 1000);

const adminApi = axios.create({
  baseURL: '/proxy/admin/v2',
  timeout: 60000,
  headers: {
    'Content-Type': 'application/json',
    Accept: '*/*'
  }
});
adminApi.interceptors.request.use((config) => {
  const store = useGlobalStore();
  if (!store.instanceId) {
    return Promise.reject(
      new Error(global.t('error.message.instance-missed')));
  }
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
    if (error.response && (error.response.status === 401
      || error.response.status === 403)) {
      debouncedError(global.t('error.message.unauthorized'));
    } else {
      const message = error.response ? error.response.data : error.message;
      debouncedError(message);
    }
    return Promise.reject(error);
  }
);

export default adminApi;
