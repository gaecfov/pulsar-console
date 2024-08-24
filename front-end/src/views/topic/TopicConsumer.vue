<script setup>
import MetricCard from '@/components/MetricCard.vue';
import { formatDate, formatRate } from '@/util/formatter';

defineProps(['consumer']);
</script>

<template>
  <Card>
    <template #title>
      <div class="flex justify-between gap-x-2">
        <div class="flex items-center gap-2">
          {{ consumer.consumerName }}
          <span class="text-muted-color text-sm">{{ $t('connectedSince') }} {{ formatDate(
            consumer.connectedSince) }}</span>
        </div>
        <div class="flex gap-x-2 items-center">
          <span class="text-muted-color text-sm">{{ consumer.address }}</span>
          <Tag>{{ consumer.clientVersion }}</Tag>
        </div>
      </div>
    </template>
    <template #content>
      <div class="card grid grid-cols-4 gap-2 p-0">
        <MetricCard title="availablePermits" :value="consumer.availablePermits" class="bg-pc-main">
        </MetricCard>
        <MetricCard title="messageAckRate" :value="formatRate(consumer.messageAckRate)" class="bg-pc-main">
        </MetricCard>
        <MetricCard title="unackedMessages" :value="consumer.unackedMessages" class="bg-pc-main">
        </MetricCard>
        <MetricCard title="lastAckedTime" :value="formatDate(consumer.lastAckedTime)"
                    class="bg-pc-main">
        </MetricCard>
        <MetricCard title="lastConsumedTime" :value="formatDate(consumer.lastConsumedTime)"
                    class="bg-pc-main">
        </MetricCard>
      </div>
    </template>
  </Card>
</template>
