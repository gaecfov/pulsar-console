<script setup>
import { useBrokerStore } from '@/views/broker/useBrokerStore';
import BrokerCard from '@/views/broker/BrokerCard.vue';

const store = useBrokerStore();

onMounted(() => {
  store.reload();
});
</script>
<template>
  <div class="grid gap-4">
    <Card>
      <template #title>{{ $t('view.broker.list.title') }}</template>
      <template #content>
        <DataView :value="store.brokers" layout="grid">
          <template #grid="{ items }">
            <div class="grid grid-cols-3 gap-4">
              <div v-for="(item, index) in items" :key="index">
                <BrokerCard :broker="item"></BrokerCard>
              </div>
            </div>
          </template>
        </DataView>
      </template>
    </Card>
  </div>
</template>
