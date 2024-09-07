<script setup>
import { listClusters } from '@/service/ClusterService';
import { useEmitter } from '@/hooks/useEmitter';

const router = useRouter();

const emitter = useEmitter();

const clusters = ref([]);
const reload = () => {
  listClusters().then((rsp) => {
    clusters.value = rsp.data.map((item) => {
      return { clusterName: item };
    });
  });
};

onActivated(() => {
  reload();
  emitter.on('instance-changed', reload);
});

onDeactivated(() => {
  emitter.off('instance-changed');
});

const goDetail = (cluster) => {
  router.push({
    name: 'cluster-detail',
    params: { cluster: cluster.clusterName }
  });
};
</script>
<template>
  <Card>
    <template #title>{{ $t('view.cluster') }}</template>
    <template #content>
      <DataTable :value="clusters">
        <Column :header="$t('view.cluster.name')">
          <template #body="{ data }">
            <Button link @click="goDetail(data)" :label="data.clusterName"></Button>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
