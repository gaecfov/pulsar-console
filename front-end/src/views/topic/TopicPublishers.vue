<script setup>
import { formatDate, formatRate, formatStorageSize } from '@/util/formatter';
import MetricCard from '@/components/MetricCard.vue';

const stats = inject('topic-stats');
const publishers = computed(() => {
  return stats.value.publishers;
});
</script>

<template>
  <DataView :value="publishers">
    <template #list="{ items }">
      <div class="flex flex-col gap-4">
        <Panel v-for="(producer, index) in items" :key="index" toggleable collapsed>
          <template #header>
            <div class="flex-grow flex justify-between gap-x-2 pr-4">
              <div class="flex items-center gap-2">
                <span class="font-bold">{{ producer.producerName }}</span>
                <Tag>{{ producer.accessMode }}</Tag>
                <span class="text-muted-color text-sm">{{ $t('connectedSince') }} {{ formatDate(producer.connectedSince) }}</span>
              </div>
              <div class="flex items-center gap-2">
                <span class="text-muted-color text-sm">{{ producer.address }}</span>
                <Tag>{{ producer.clientVersion }}</Tag>
              </div>
            </div>
          </template>
          <div class="grid grid-cols-4 gap-2">
            <MetricCard class="bg-pc-main">
              <template #title>{{ $t('producerId') }}</template>
              <template #value>{{ producer.producerId }}</template>
            </MetricCard>
            <MetricCard class="bg-pc-main">
              <template #title>{{ $t('msgRateIn') }}</template>
              <template #value>{{ formatRate(producer.msgRateIn) }}</template>
            </MetricCard>
            <MetricCard class="bg-pc-main">
              <template #title>{{ $t('averageMsgSize') }}</template>
              <template #value>{{ formatStorageSize(producer.averageMsgSize) }}</template>
            </MetricCard>
          </div>
        </Panel>
      </div>
    </template>
  </DataView>
</template>
