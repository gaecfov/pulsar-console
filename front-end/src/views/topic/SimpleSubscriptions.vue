<script setup>
import { formatRate } from '../../util/formatter';

const stats = inject('topic-stats');
const subscriptions = computed(() => {
  if (stats.value.subscriptions) {
    return Object.keys(stats.value.subscriptions).map((x) => {
      return { subscription: x, ...stats.value.subscriptions[x] };
    });
  }
  return [];
});
</script>

<template>
  <Panel :header="$t('subscriptions')" toggleable>
    <DataView :value="subscriptions">
      <template #list="{ items }">
        <div class="flex flex-col gap-4">
          <div class="flex flex-col pt-2" v-for="(item, index) in items" :key="index" :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
            <div class="flex justify-between">
              <span class="font-bold">{{ item.subscription }}</span>
              <div>
                <Tag>{{ item.type }}</Tag>
              </div>
            </div>
            <div class="flex gap-2">
              <Metric title="msgBacklog" :value="item.msgBacklog"></Metric>
              <Metric title="messageAckRate" :value="formatRate(item.messageAckRate)"></Metric>
              <Metric title="unackedMessages" :value="item.unackedMessages"></Metric>
            </div>
          </div>
        </div>
      </template>
    </DataView>
  </Panel>
</template>
