<script setup>
import { getClusterConfig } from '@/service/ClusterService';
import Description from '@/components/Description.vue';

const dialogRef = inject('dialogRef');
const stats = ref({});
provide('cluster-stats', stats);
onMounted(() => {
  const cluster = dialogRef.value.data.cluster;
  getClusterConfig(cluster).then((res) => {
    stats.value = res.data;
  });
});
</script>

<template>
  <ScrollPanel class="h-[80dvh] p-4">
    <div class="grid gap-2">
      <Description title="serviceUrl" :content="stats.serviceUrl"></Description>
      <Description title="serviceUrlTls" :content="stats.serviceUrlTls"></Description>
      <Description title="brokerServiceUrl" :content="stats.brokerServiceUrl"></Description>
      <Description title="brokerServiceUrlTls" :content="stats.brokerServiceUrlTls"></Description>
      <Description title="brokerClientTlsEnabled" :content="stats.brokerClientTlsEnabled"></Description>
      <Description title="tlsAllowInsecureConnection" :content="stats.tlsAllowInsecureConnection"></Description>
      <Description title="brokerClientTlsEnabledWithKeyStore" :content="stats.brokerClientTlsEnabledWithKeyStore"></Description>
      <Description title="brokerClientTlsTrustStoreType" :content="stats.brokerClientTlsTrustStoreType"></Description>
      <Description title="brokerClientTlsKeyStoreType" :content="stats.brokerClientTlsKeyStoreType"></Description>
    </div>
  </ScrollPanel>
</template>
