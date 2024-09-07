<script setup>
import { listClusters } from '@/service/ClusterService';

const clusters = ref([]);
const model = defineModel();

onMounted(() => {
  listClusters().then((rsp) => {
    clusters.value = rsp.data;
  });
});
</script>

<template>
  <MultiSelect v-bind="$attrs" v-model="model" filter display="chip" :options="clusters" :placeholder="$t('placeholder.select')" />
</template>
