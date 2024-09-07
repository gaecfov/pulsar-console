<script setup>

import { listClientCertificates } from '@/service/ClientCertificateService';

const clientCertificates = ref([]);
const model = defineModel();

onMounted(() => {
  listClientCertificates().then((rsp) => {
    clientCertificates.value = rsp.data;
  });
});
</script>

<template>
  <Select v-bind="$attrs" v-model="model" filter optionLabel="name" optionValue="id"
          :options="clientCertificates" :placeholder="$t('placeholder.select')" />
</template>
