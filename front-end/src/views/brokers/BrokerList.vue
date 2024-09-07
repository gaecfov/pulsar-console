<script setup>
import { useBrokerStore } from '@/stroes/useBrokerStore';
import { useEmitter } from '@/hooks/useEmitter';
import { useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();
const store = useBrokerStore();
const emitter = useEmitter();

onActivated(() => {
  store.reload();
  emitter.on('instance-changed', store.reload);
});

onDeactivated(() => {
  emitter.off('instance-changed');
});

const goDetail = (broker) => {
  router.push({
    name: 'broker-detail', params: {
      cluster: route.params.cluster,
      broker: broker.brokerId
    }
  });
};
</script>
<template>
  <DataView :value="store.brokers">
    <template #list="{items}">
      <div class="flex flex-col">
        <div v-for="(item, index) in items" :key="index">
          <div class="flex sm:flex-row sm:items-center p-2 gap-4"
               :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
            <Button link @click="goDetail(item)" :label="item.brokerId"></Button>
            <Tag v-if="item.brokerId===store.leaderBrokerId">Leader</Tag>
          </div>
        </div>
      </div>
    </template>
  </DataView>
</template>
