import * as ins from '@/service/InstanceService';
import toast from '@/util/toast-util';

export const useInstanceStore = defineStore('instance-store', () => {
  const instances = ref([]);
  const loading = ref(false);

  const reload = () => {
    loading.value = true;
    ins.listInstances().then((res) => {
      instances.value = res.data;
      loading.value = false;
    });
  };

  const createInstance = (instance) => {
    return ins.createInstance(instance).then(() => {
      toast.success();
      reload();
    });
  };

  const modifyInstance = (id, instance) => {
    return ins.modifyInstance(id, instance).then(() => {
      toast.success();
      reload();
    });
  };

  const deleteInstance = async (instanceId) => {
    return ins.deleteInstance(instanceId).then(() => {
      toast.success();
      reload();
    });
  };

  return {
    instances,
    loading,
    reload,
    createInstance,
    modifyInstance,
    deleteInstance
  };
});
