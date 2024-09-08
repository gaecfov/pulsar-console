<script setup>
import { getClusterConfig } from '@/service/ClusterService';
import DetailPage from '@/components/DetailPage.vue';

const props = defineProps(['cluster']);
const stats = ref({});
onMounted(() => {
  getClusterConfig(props.cluster).then((res) => {
    stats.value = res.data;
  });
});
</script>

<template>
  <DetailPage back-route="clusters">
    <template #title>
      {{ cluster }}
    </template>
    <Tabs value="brokers" class="mb-4">
      <TabList>
        <Tab value="brokers">
          <router-link :to="{name:'broker-list',params:{cluster:cluster}}">
            {{ $t('view.broker') }}
          </router-link>
        </Tab>
      </TabList>
    </Tabs>
    <router-view></router-view>
  </DetailPage>
</template>
