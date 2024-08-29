import adminApi from '@/util/pulsar-api';

export const listBrokers = () => {
  return adminApi.get('/brokers');
};

export const getLeaderBroker = () => {
  return adminApi.get('/brokers/leaderBroker');
};


export const checkHealth = (brokerId) => {
  return adminApi.get('/brokers/health', {
    headers: {
      'X-ORIGIN-DOMAIN': brokerId
    }
  });
};
export const checkReady = (brokerId) => {
  return adminApi.get('/brokers/ready', {
    headers: {
      'X-ORIGIN-DOMAIN': brokerId
    }
  });
};

export const getRuntimeConfiguration = (brokerId) => {
  return adminApi.get('/brokers/configuration/runtime', {
    headers: {
      'X-ORIGIN-DOMAIN': brokerId
    }
  });
};

export const getInternalConfiguration = (brokerId) => {
  return adminApi.get('/brokers/internal-configuration', {
    headers: {
      'X-ORIGIN-DOMAIN': brokerId
    }
  });
};

export const shutdownBroker = (brokerId) => {
  return adminApi.post('/brokers/shutdown', {
    headers: {
      'X-ORIGIN-DOMAIN': brokerId
    }
  });
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
