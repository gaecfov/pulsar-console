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

export const getNamespacePolicies = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}`);
};

export const getRetentionConfig = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/retention`);
};

export const setRetentionConfig = (tenant, namespace, config) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/retention`, config);
};

export const getNamespaceMessageTTL = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/messageTTL`);
};

export const setNamespaceMessageTTL = (tenant, namespace, num) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/messageTTL`, num);
};

export const removeNamespaceMessageTTL = (tenant, namespace) => {
  return adminApi.delete(`/namespaces/${tenant}/${namespace}/messageTTL`);
};


export const unloadNamespace = (tenant, namespace) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}/unload`);
};
