import adminApi from '@/util/pulsar-api';

export const listNamespaces = (tenant) => {
  return adminApi.get(`/namespaces/${tenant}`);
};
export const createNamespace = (tenant, namespace) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}`);
};

export const deleteNamespace = (tenant, namespace) => {
  return adminApi.delete(`/namespaces/${tenant}/${namespace}`);
};

export const getNamespaceConfig = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}`);
};

export const getRetentionConfig = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/retention`);
};

export const setRetentionConfig = (tenant, namespace, config) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/retention`, config);
};

export const getNamespaceMessageTTL = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/messageTTL/getNamespaceMessageTTL`);
};

export const setNamespaceMessageTTL = (tenant, namespace, config) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/messageTTL/setNamespaceMessageTTL`, config);
};

export const unloadNamespace = (tenant, namespace) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}/unload`);
};
