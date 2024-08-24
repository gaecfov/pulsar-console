import adminApi from '@/util/pulsar-api';

export const listBrokers = () => {
  return adminApi.get('/brokers');
};

export const checkHealth = () => {
  return adminApi.get('/brokers/health');
};
export const checkReady = () => {
  return adminApi.get('/brokers/ready');
};

export const getLeaderBroker = () => {
  return adminApi.get('/brokers/leaderBroker');
};

export const getRuntimeConfiguration = () => {
  return adminApi.get('/brokers/configuration/runtime');
};

export const getInternalConfiguration = () => {
  return adminApi.get('/brokers/internal-configuration');
};

export const shutdownBroker = () => {
  return adminApi.post('/brokers/shutdown');
};

export const listOwnedNamespaces = (cluster, brokerId) => {
  return adminApi.post(`/brokers/${cluster}/${brokerId}/ownedNamespaces`);
};

export const getLoadReport = (brokerId) => {
  return adminApi.get('/broker-stats/load-report', {
    headers: {
      'X-ORIGIN-DOMAIN': brokerId
    }
  });
};
