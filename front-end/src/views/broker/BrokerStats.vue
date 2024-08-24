<script setup>
import { getLoadReport } from '@/service/BrokerService';
import MetricCard from '@/components/MetricCard.vue';
import { fixedNum, formatRate, formatThroughput } from '../../util/formatter';

const dialogRef = inject('dialogRef');
const broker = dialogRef.value.data.broker;
const loadReport = ref();
const bundlesCollapsed = ref(true);
onMounted(() => {
  getLoadReport().then((res) => {
    loadReport.value = res.data;
  });
});
</script>

<template>
  <div v-if="loadReport" class="flex card p-4 flex-col bg-pc-main gap-4">
    <div class="flex justify-between items-center">
      {{ broker.brokerId }}
      <Tag>{{ loadReport.brokerVersionString }}</Tag>
    </div>
    <div class="flex flex-col gap-4">
      <div class="grid grid-cols-6 gap-2">
        <MetricCard>
          <template #title>{{ $t('cpu') }}</template>
          <template #value> {{ fixedNum(loadReport.cpu.usage) }}/{{ fixedNum(loadReport.cpu.limit) }} </template>
        </MetricCard>
        <MetricCard>
          <template #title>{{ $t('memory') }}</template>
          <template #value> {{ fixedNum(loadReport.memory.usage) }}/{{ fixedNum(loadReport.memory.limit) }} </template>
        </MetricCard>
        <MetricCard>
          <template #title>{{ $t('directMemory') }}</template>
          <template #value> {{ fixedNum(loadReport.directMemory.usage) }}/{{ fixedNum(loadReport.directMemory.limit) }} </template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('msgRateIn') }}</template>
          <template #value> {{ formatRate(loadReport.msgRateIn) }}</template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('msgRateOut') }}</template>
          <template #value> {{ formatRate(loadReport.msgRateOut) }}</template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('msgThroughputIn') }}</template>
          <template #value> {{ formatThroughput(loadReport.msgThroughputIn) }}</template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('msgThroughputOut') }}</template>
          <template #value> {{ formatThroughput(loadReport.msgThroughputOut) }}</template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('numTopics') }}</template>
          <template #value> {{ loadReport.numTopics }}</template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('numBundles') }}</template>
          <template #value> {{ loadReport.numBundles }}</template>
        </MetricCard>

        <MetricCard>
          <template #title>{{ $t('numConsumers') }}</template>
          <template #value> {{ loadReport.numConsumers }}</template>
        </MetricCard>

        <Panel class="col-span-6 p-0" toggleable :collapsed="bundlesCollapsed">
          <template #header>
            <div class="flex items-center gap-2">
              <span class="font-bold">Bundles</span>
            </div>
          </template>
          <div class="card p-0 gap-2 grid grid-cols-2">
            <Tag v-for="(item, index) in loadReport.bundles" :key="index">{{ item }}</Tag>
          </div>
        </Panel>
      </div>
    </div>
  </div>
</template>
