import * as ins from '@/service/ClientTokenService';
import toast from '@/util/toast-util';

export const useClientTokenStore = defineStore('client-token-store', () => {
  const clientTokens = ref([]);
  const loading = ref(false);

  const reload = () => {
    loading.value = true;
    ins.listClientTokens().then((res) => {
      clientTokens.value = res.data;
      loading.value = false;
    });
  };

  const createClientToken = (clientToken) => {
    return ins.createClientToken(clientToken).then(() => {
      toast.success();
      reload();
    });
  };

  const modifyClientToken = (id, clientToken) => {
    return ins.modifyClientToken(id, clientToken).then(() => {
      toast.success();
      reload();
    });
  };

  const deleteClientToken = async (clientTokenId) => {
    return ins.deleteClientToken(clientTokenId).then(() => {
      toast.success();
      reload();
    });
  };

  return {
    clientTokens,
    loading,
    reload,
    createClientToken,
    modifyClientToken,
    deleteClientToken
  };
});
