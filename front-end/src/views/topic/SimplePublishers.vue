<script setup>
import { formatRate, formatStorageSize } from '@/util/formatter';

const stats = inject('topic-stats');
const publishers = computed(() => {
  return stats.value.publishers;
});
</script>

<template>
  <Panel :header="$t('publishers')" toggleable collapsed>
    <DataView :value="publishers">
      <template #list="{ items }">
        <div class="flex flex-col gap-4">
          <div class="flex flex-col pt-2" v-for="(item, index) in items" :key="index" :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
            <div class="flex justify-between">
              <span class="font-bold">{{ item.producerName }}</span>
              <div class="flex gap-2 items-center">
                <span class="text-muted-color text-sm">{{ item.address }}</span>
                <Tag>{{ item.accessMode }}</Tag>
                <Tag>{{ item.clientVersion }}</Tag>
              </div>
            </div>
            <div class="flex gap-2">
              <Metric title="producerId" :value="item.producerId"></Metric>
              <Metric title="msgRateIn" :value="formatRate(item.msgRateIn)"></Metric>
              <Metric title="averageMsgSize" :value="formatStorageSize(item.averageMsgSize)"></Metric>
            </div>
          </div>
        </div>
      </template>
    </DataView>
  </Panel>
</template>
