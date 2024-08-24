import adminApi from '@/util/pulsar-api';

export const listTenants = () => {
  return adminApi.get('/tenants');
};

export const createTenant = (tenant, tenantModel) => {
  return adminApi.put(`/tenants/${tenant}`, tenantModel);
};

export const deleteTenant = (tenant) => {
  return adminApi.delete(`/tenants/${tenant}`);
};

export const getTenantConfig = (tenant) => {
  return adminApi.get(`/tenants/${tenant}`);
};
