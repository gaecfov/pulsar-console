import {
  createInstance,
  deleteInstance,
  listInstances
} from '@/service/InstanceService';
import toast from '@/util/toast-util';
import { useGlobalStore } from '@/stroes/useGlobalStore';

export const useInstanceStore = defineStore('instance-store', () => {
  const instances = ref([]);
  const loading = ref(false);

  const reload = () => {
    loading.value = true;
    listInstances().then((res) => {
      instances.value = res.data;

      const store = useGlobalStore();
      if (res.data.length > 0) {
        if (!res.data.some(item => item.id === store.instanceId)) {
          store.instanceId = res.data[0].id;
        }
      }
      loading.value = false;
    });
  };

  const addInstance = (instance) => {
    return createInstance(instance).then(() => {
      toast.success();
      reload();
    });
  };
  const delInstance = async (instanceId) => {
    return deleteInstance(instanceId).then(() => {
      toast.success();
      reload();
    });
  };

  return { instances, loading, reload, addInstance, delInstance };
});
