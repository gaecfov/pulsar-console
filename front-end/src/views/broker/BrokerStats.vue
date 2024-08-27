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
        <MetricCard title="cpu">
          <template #value>
            <Badge>
              {{ fixedNum(loadReport.cpu.usage) }}/{{ fixedNum(loadReport.cpu.limit) }}
            </Badge>
          </template>
        </MetricCard>
        <MetricCard title="memory">
          <template #value>
            <Badge>{{ fixedNum(loadReport.memory.usage) }}/{{ fixedNum(
              loadReport.memory.limit) }}
            </Badge>
          </template>
        </MetricCard>
        <MetricCard title="directMemory" unit="MB">
          <template #value>
            <Badge>{{ fixedNum(loadReport.directMemory.usage) }}/{{ fixedNum(
              loadReport.directMemory.limit) }}
            </Badge>
          </template>
        </MetricCard>

        <MetricCard title="msgRateIn" :value="formatRate(loadReport.msgRateIn)">
          <template #title>{{ $t('msgRateIn') }}</template>
        </MetricCard>

        <MetricCard title="msgRateOut" :value="formatRate(loadReport.msgRateOut)">
        </MetricCard>

        <MetricCard title="msgThroughputIn" :value="formatThroughput(loadReport.msgThroughputIn)">
        </MetricCard>

        <MetricCard title="msgThroughputOut" :value="formatThroughput(loadReport.msgThroughputOut)">
        </MetricCard>

        <MetricCard title="numTopics" :value="loadReport.numTopics">
        </MetricCard>

        <MetricCard title="numBundles" :value="loadReport.numBundles">
        </MetricCard>

        <MetricCard title="numConsumers" :value="loadReport.numConsumers">
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
