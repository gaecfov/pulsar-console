import adminApi from '@/util/pulsar-api';

export const listClusters = () => {
  return adminApi.get('/clusters');
};

export const getClusterConfig = (cluster) => {
  return adminApi.get(`/clusters/${cluster}`);
};
