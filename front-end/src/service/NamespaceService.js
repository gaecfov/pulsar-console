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

export const getPolicies = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}`);
};
export const getPolicy = (tenant, namespace, policyName, params) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/${policyName}`,
    { params });
};

export const postPolicy = (tenant, namespace, policyName, policy, params) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/${policyName}`,
    policy, { params });
};

export const putPolicy = (tenant, namespace, policyName, policy, params) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}/${policyName}`,
    policy, { params });
};

export const removePolicy = (tenant, namespace, policyName, params) => {
  return adminApi.delete(`/namespaces/${tenant}/${namespace}/${policyName}`,
    { params });
};

export const unload = (tenant, namespace) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}/unload`);
};
export const unloadBundle = (tenant, namespace, bundle) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}/${bundle}/unload`);
};

export const clearBacklog = (tenant, namespace) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/clearBacklog`);
};

export const clearBundleBacklog = (tenant, namespace, bundle) => {
  return adminApi.post(
    `/namespaces/${tenant}/${namespace}/${bundle}/clearBacklog`);
};

export const deleteBundle = (tenant, namespace, bundle) => {
  return adminApi.delete(`/namespaces/${tenant}/${namespace}/${bundle}`);
};


export const splitBundle = (tenant, namespace, bundle, body, params) => {
  return adminApi.put(`/namespaces/${tenant}/${namespace}/${bundle}/split`,
    body, { params });
};

export const retrievePermissions = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/permissions`);
};

export const grantPermission = (tenant, namespace, role, permissions) => {
  return adminApi.post(`/namespaces/${tenant}/${namespace}/permissions/${role}`,
    permissions);
};

export const revokeAllPermissions = (tenant, namespace, role) => {
  return adminApi.delete(
    `/namespaces/${tenant}/${namespace}/permissions/${role}`);
};
