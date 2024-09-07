<script setup>
import { formatDate, formatRate, formatStorageSize } from '@/util/formatter';

const stats = inject('topic-stats');
const publishers = computed(() => {
  return stats.value ? stats.value.publishers : [];
});
</script>

<template>
  <DataView :value="publishers">
    <template #list="{ items }">
      <div class="flex flex-col gap-4">
        <Panel v-for="(producer, index) in items" :key="index">
          <template #header>
            <div class="flex-grow flex justify-between gap-x-2 pr-4">
              <div class="flex items-center gap-2">
                <span class="font-bold">{{ producer.producerName }}</span>
                <Tag>{{ producer.accessMode }}</Tag>
                <span class="text-muted-color text-sm">{{ $t('connectedSince') }} {{ formatDate(
                  producer.connectedSince) }}</span>
                <span class="text-muted-color text-sm">{{ producer.address }}</span>
                <Tag>{{ producer.clientVersion }}</Tag>
              </div>
            </div>
          </template>
          <div class="grid grid-cols-4 gap-2">
            <Metric title="producerId" :value="producer.producerId"></Metric>
            <Metric title="msgRateIn" :value="formatRate(producer.msgRateIn)"></Metric>
            <Metric title="averageMsgSize"
                    :value="formatStorageSize(producer.averageMsgSize)"></Metric>
          </div>
        </Panel>
      </div>
    </template>
  </DataView>
</template>
