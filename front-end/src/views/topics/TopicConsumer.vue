<script setup>
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
          <span class="text-muted-color text-sm">{{ consumer.address }}</span>
          <Tag>{{ consumer.clientVersion }}</Tag>
        </div>
      </div>
    </template>
    <template #content>
      <div class="card grid grid-cols-5 gap-2 p-0">
        <Metric title="availablePermits" :value="consumer.availablePermits">
        </Metric>
        <Metric title="messageAckRate" :value="formatRate(consumer.messageAckRate)">
        </Metric>
        <Metric title="unackedMessages" :value="consumer.unackedMessages">
        </Metric>
        <Metric title="lastAckedTime" :value="formatDate(consumer.lastAckedTime)">
        </Metric>
        <Metric title="lastConsumedTime" :value="formatDate(consumer.lastConsumedTime)">
        </Metric>
      </div>
    </template>
  </Card>
</template>
