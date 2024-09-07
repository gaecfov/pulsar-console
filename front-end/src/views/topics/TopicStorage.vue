<script setup>

import MetricCard from '@/components/MetricCard.vue';
import { trim, truncate } from '@/service/TopicService';
import toastUtil from '@/util/toast-util';
import ConfirmButton from '@/components/ConfirmButton.vue';

const topic = inject('topic');
const stats = inject('topic-stats');
const internalStats = inject('topic-internal-stats');

const ledgers = computed(() => {
  if (internalStats.value) {
    return internalStats.value.ledgers;
  }
  return [];
});
const cursors = computed(() => {
  if (internalStats.value) {
    return Object.keys(internalStats.value.cursors).map(k => {
      return {
        cursor: k,
        ...internalStats.value.cursors[k]
      };
    });
  }
  return [];
});

</script>
<template>
  <div class="grid grid-cols-4 gap-4">
    <template v-if="stats">
      <MetricCard icon="pi-database" title="view.topic.storage.storageSize" :value="stats.storageSize"
                  value-type="storage">
        <template #action>
          <div class="flex gap-2">
            <ConfirmButton size="small" icon="pi pi-refresh"
                           @confirm="trim(topic).then(()=>toastUtil.success())"
                           :label="$t('action.trim')"></ConfirmButton>
            <ConfirmButton size="small" icon="pi pi-sign-out"
                           @confirm="truncate(topic).then(()=>toastUtil.success())"
                           :label="$t('action.truncate')"></ConfirmButton>
          </div>
        </template>
      </MetricCard>

    </template>

    <template v-if="internalStats">
      <MetricCard icon="pi-file" title="view.topic.storage.numberOfEntries" :value="internalStats.numberOfEntries">
      </MetricCard>
    </template>

    <Panel class="col-span-4" header="ledgers">
      <DataTable :value="ledgers">
        <Column field="ledgerId" header="ledgerId"></Column>
        <Column field="entries" header="entries"></Column>
        <Column field="size" header="size"></Column>
        <Column field="offloaded" header="offloaded"></Column>
        <Column field="underReplicated" header="underReplicated"></Column>
      </DataTable>
    </Panel>

    <Panel class="col-span-4" header="cursors">
      <DataTable :value="cursors">
        <Column field="cursor" header="cursor"></Column>
        <Column field="markDeletePosition" header="markDeletePosition"></Column>
        <Column field="readPosition" header="readPosition"></Column>
        <Column field="waitingReadOp" header="waitingReadOp"></Column>
        <Column field="pendingReadOps" header="pendingReadOps"></Column>
        <Column field="numberOfEntriesSinceFirstNotAckedMessage"
                header="numberOfEntriesSinceFirstNotAckedMessage"></Column>
      </DataTable>
    </Panel>
  </div>
</template>
