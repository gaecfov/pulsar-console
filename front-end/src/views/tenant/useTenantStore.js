import * as ts from '@/service/TenantService';
import toast from '@/util/toast-util';

export const useTenantStore = defineStore('tenant-store', () => {
  const tenants = ref([]);

  const loading = ref(false);
  const reload = () => {
    loading.value = true;
    ts.listTenants().then((res) => {
      tenants.value = res.data.map((x) => {
        return { tenantName: x };
      });
      loading.value = false;
    });
  };

  const createTenant = (tenant, tenantModel) => {
    return ts.createTenant(tenant, tenantModel).then(() => {
      toast.success();
      reload();
    });
  };

  const deleteTenant = (tenant) => {
    return ts.deleteTenant(tenant).then(() => {
      toast.success();
      reload();
    });
  };

  return { tenants, loading, createTenant, deleteTenant, reload };
});
