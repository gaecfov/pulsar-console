<script setup>
import { listClusters } from '@/service/ClusterService';
import { useDialog } from 'primevue/usedialog';
import ClusterStats from '@/views/cluster/ClusterStats.vue';

const dialog = useDialog();

const clusters = ref([]);
onMounted(() => {
  listClusters().then((rsp) => {
    clusters.value = rsp.data.map((item) => {
      return { clusterName: item };
    });
  });
});

const showClusterStats = (cluster) => {
  dialog.open(ClusterStats, {
    props: {
      header: `${cluster.clusterName}`,
      modal: true,
      style: {
        width: '60vw'
      }
    },
    data: { cluster: cluster.clusterName }
  });
};
</script>
<template>
  <Card>
    <template #title>{{ $t('view.cluster.list.title') }}</template>
    <template #content>
      <DataTable :value="clusters">
        <Column :header="$t('name')">
          <template #body="{ data }">
            <Button link @click="showClusterStats(data)" :label="data.clusterName"></Button>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
