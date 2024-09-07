import * as ins from '@/service/ClientCertificateService';
import toast from '@/util/toast-util';

export const useClientCertificateStore = defineStore('client-certificate-store', () => {
  const clientCertificates = ref([]);
  const loading = ref(false);

  const reload = () => {
    loading.value = true;
    ins.listClientCertificates().then((res) => {
      clientCertificates.value = res.data;
      loading.value = false;
    });
  };

  const createClientCertificate = (clientCertificate) => {
    return ins.createClientCertificate(clientCertificate).then(() => {
      toast.success();
      reload();
    });
  };

  const modifyClientCertificate = (id, clientCertificate) => {
    return ins.modifyClientCertificate(id, clientCertificate).then(() => {
      toast.success();
      reload();
    });
  };

  const deleteClientCertificate = async (clientCertificateId) => {
    return ins.deleteClientCertificate(clientCertificateId).then(() => {
      toast.success();
      reload();
    });
  };

  return {
    clientCertificates,
    loading,
    reload,
    createClientCertificate,
    modifyClientCertificate,
    deleteClientCertificate
  };
});
