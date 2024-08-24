import * as ns from '@/service/NamespaceService';
import toast from '@/util/toast-util';

export const useNamespaceStore = defineStore('namespace-store', () => {
  const tenant = ref('');
  const namespaces = ref([]);
  const loading = ref(false);
  const reload = () => {
    loading.value = true;
    ns.listNamespaces(tenant.value).then((res) => {
      namespaces.value = res.data.map((x) => {
        return { namespaceName: x };
      });
      loading.value = false;
    });
  };

  const createNamespace = (namespace) => {
    return ns.createNamespace(tenant.value, namespace).then(() => {
      toast.success();
      reload();
    });
  };
  const deleteNamespace = (namespace) => {
    return ns.deleteNamespace(tenant.value, namespace).then(() => {
      toast.success();
      reload();
    });
  };

  return {
    tenant,
    namespaces,
    loading,
    reload,
    createNamespace,
    deleteNamespace
  };
});
