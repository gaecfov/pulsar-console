<script setup>
import { useRouter } from 'vue-router';
import { formatDate } from '@/util/formatter';
import {
  deleteClientCertificate,
  download,
  listClientCertificates
} from '@/service/ClientCertificateService';
import { useAsyncDownload } from '@/hooks/useAsyncDownload';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import toastUtil from '@/util/toast-util';

const clientCertificates = ref([]);

onActivated(() => {
  reload();
});

const reload = () => {
  listClientCertificates().then(res => {
    clientCertificates.value = res.data;
  });
};

const router = useRouter();
const goDetail = (clientCertificate) => {
  if (clientCertificate) {
    router.push(
      { name: 'client-certificate-detail', params: { clientCertificateId: clientCertificate.id } });
  } else {
    router.push({ name: 'client-certificate-new' });
  }
};

const { downloadBlob } = useAsyncDownload();
const downloadFile = async (id) => {
  const response = await download(id);
  downloadBlob(response);
};

const deleteCertificate = (certificate) => {
  deleteClientCertificate(certificate.id).then(() => {
    toastUtil.success();
    reload();
  });
};
</script>
<template>
  <Card>
    <template #title> {{ $t('view.client-certificate') }}</template>
    <template #content>
      <DataTable :value="clientCertificates">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <Button :label="$t('view.client-certificate.action.new')" icon="pi pi-plus"
                    @click="goDetail()" />
          </div>
        </template>
        <Column field="name" :header="$t('view.client-certificate.name')">
          <template #body="{data}">
            <Button link @click="goDetail(data)" :label="data.name"></Button>
          </template>
        </Column>
        <Column field="description" :header="$t('view.client-certificate.description')"></Column>
        <Column field="algorithm" :header="$t('view.client-certificate.algorithm')"></Column>
        <Column field="certificateType"
                :header="$t('view.client-certificate.certificateType')"></Column>
        <Column field="createdAt" :header="$t('view.client-certificate.createdAt')">
          <template #body="{data}">
            {{ formatDate(data.createdAt) }}
          </template>
        </Column>
        <Column>
          <template #body="{data}">
            <div class="flex gap-4">
              <Button outlined v-tooltip="$t('action.download')" icon="pi pi-download"
                      @click="downloadFile(data.id)"></Button>
              <ConfirmDeleteButton @confirm="deleteCertificate(data)"></ConfirmDeleteButton>
            </div>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
