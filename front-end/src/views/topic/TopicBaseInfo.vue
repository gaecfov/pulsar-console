<script setup>
import MetricCard from '@/components/MetricCard.vue';
import {formatRate, formatStorageSize, formatThroughput} from "@/util/formatter";

const stats = inject('topic-stats');
const topic = toRaw(inject('topic'));
</script>

<template>
  <Panel toggleable>
    <template #header>
      <div class="flex-grow flex justify-between pr-4">
        <div class="font-bold text-lg">{{ topic.fullTopic }}</div>
        <div class="text-md text-surface-500">{{ $t('ownerBroker') }}：{{ stats.ownerBroker }}</div>
      </div>
    </template>
    <div class="flex flex-col gap-y-4">
      <div class="grid grid-cols-5 gap-2">
        <MetricCard class="bg-pc-main">
          <template #title>{{ $t('msgRateIn') }}</template>
          <template #value>
            {{ formatRate(stats.msgRateIn) }}
          </template>
        </MetricCard>
        <MetricCard class="bg-pc-main">
          <template #title>{{ $t('msgRateOut') }}</template>
          <template #value>
            {{ formatRate(stats.msgRateOut) }}
          </template>
        </MetricCard>
        <MetricCard class="bg-pc-main">
          <template #title>{{ $t('msgThroughputIn') }}</template>
          <template #value>
            {{ formatThroughput(stats.msgThroughputIn) }}
          </template>
        </MetricCard>
        <MetricCard class="bg-pc-main">
          <template #title>{{ $t('msgThroughputOut') }}</template>
          <template #value>
            {{ formatThroughput(stats.msgThroughputOut) }}
          </template>
        </MetricCard>
        <MetricCard class="bg-pc-main">
          <template #title>{{ $t('storageSize') }}</template>
          <template #value>
            {{ formatStorageSize(stats.storageSize) }}
          </template>
        </MetricCard>
      </div>
    </div>
  </Panel>
</template>
