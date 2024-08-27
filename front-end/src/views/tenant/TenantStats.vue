<script setup>
import { getTenantConfig } from '@/service/TenantService';
import MetricCard from '@/components/MetricCard.vue';

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
  <div class="flex card p-4 flex-col bg-pc-main gap-4">
    <MetricCard title="adminRoles" :value="stats.adminRoles">
      <template #value="{data}">
        <div class="flex gap-2">
          <Chip v-for="(item,index) in data" :key="index">{{ item }}</Chip>
        </div>
      </template>
    </MetricCard>

    <MetricCard title="allowedClusters" :value="stats.allowedClusters">
      <template #value="{data}">
        <div class="flex gap-2">
          <Chip v-for="(item,index) in data" :key="index">{{ item }}</Chip>
        </div>
      </template>
    </MetricCard>
  </div>
</template>
