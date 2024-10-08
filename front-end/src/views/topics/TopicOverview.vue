<script setup>
import MetricCard from '@/components/MetricCard.vue';
import ConfirmButton from '@/components/ConfirmButton.vue';
import * as ts from '@/service/TopicService';
import toastUtil from '@/util/toast-util';
import {useRouter} from "vue-router";

const topic = inject('topic');
const stats = inject('topic-stats');
const internalStats = inject('topic-internal-stats');
const router = useRouter();
const unload = () => {
  ts.unload(topic.value).then(() => {
    toastUtil.success()
  });
}
const deleteTopic = () => {
  ts.deleteTopic(topic.value).then(() => {
    toastUtil.success()
    router.back()
  });
}
const terminate = () => {
  ts.terminate(topic.value).then(() => {
    toastUtil.success()
  });
}
</script>
<template>
  <div class="grid grid-cols-4 gap-4">
    <div class="col-span-4">
      <Toolbar>
        <template #start>
          <div class="flex gap-2">
            <ConfirmButton size="small" icon="pi pi-sync"
                           @confirm="unload"
                           :label="$t('view.topic.action.unload')"></ConfirmButton>
            <ConfirmButton size="small" icon="pi pi-trash" severity="danger"
                           @confirm="deleteTopic"
                           :label="$t('view.topic.action.delete')"></ConfirmButton>
            <ConfirmButton size="small" icon="pi pi-ban"
                           severity="danger"
                           @confirm="terminate"
                           :label="$t('view.topic.action.terminate')"></ConfirmButton>
          </div>
        </template>
      </Toolbar>
    </div>
    <template v-if="internalStats">
      <MetricCard icon="pi pi-box" title="view.topic.state" :value="internalStats.state">
      </MetricCard>
    </template>
    <template v-if="stats">
      <MetricCard icon="pi pi-desktop" title="view.topic.ownerBroker" :value="stats.ownerBroker">
      </MetricCard>
      <MetricCard icon="pi pi-arrow-down" title="view.topic.msgRateIn" :value="stats.msgRateIn"
                  value-type="rate">
      </MetricCard>
      <MetricCard icon="pi pi-arrow-up" title="view.topic.msgRateOut" :value="stats.msgRateOut"
                  value-type="rate">
      </MetricCard>
      <MetricCard icon="pi pi-globe" title="view.topic.msgThroughputIn" :value="stats.msgThroughputIn"
                  value-type="throughput">
      </MetricCard>
      <MetricCard icon="pi pi-globe" title="view.topic.msgThroughputOut" :value="stats.msgThroughputOut"
                  value-type="throughput">
      </MetricCard>
    </template>
  </div>
</template>
