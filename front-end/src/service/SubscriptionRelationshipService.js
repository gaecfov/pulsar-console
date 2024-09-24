import consoleApi from '@/util/console-api';

export const search = (params) => {
  return consoleApi.get('/subscription-relationship/search', { params });
};

export const deleteByTopic = (instanceId, topic) => {
  return consoleApi.delete(`/subscription-relationship`, {
    params: {
      instanceId, topic
    }
  });
};

export const saveAll = (data) => {
  return consoleApi.post(
    `/subscription-relationship/saveAll`, data);
};

export const refresh = (instanceId, tenant, namespace) => {
  return consoleApi.get(
    `/subscription-relationship/${instanceId}/${tenant}/${namespace}/refresh`);
};
