<script setup>
import { getTenantConfig } from '@/service/TenantService';
import Description from '@/components/Description.vue';

const dialogRef = inject('dialogRef');
const stats = ref({});
provide('namespace-stats', stats);
onMounted(() => {
  const tenant = dialogRef.value.data.tenant;
  getTenantConfig(tenant).then((res) => {
    stats.value = res.data;
  });
});
</script>

<template>
  <div class="grid gap-2">
    <Description title="adminRoles" :content="stats.adminRoles"></Description>
    <Description title="allowedClusters" :content="stats.allowedClusters"></Description>
  </div>
</template>
