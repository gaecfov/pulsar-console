<script setup>
import { useConfirm } from 'primevue/useconfirm';
import MetricCard from '@/components/MetricCard.vue';
import { useDialog } from 'primevue/usedialog';
import Metric from '@/components/Metric.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const dialog = useDialog();
const confirm = useConfirm();

const stats = inject('namespace-stats');
</script>

<template>
  <div class="grid grid-cols-6 gap-4">
    <MetricCard title="message_ttl_in_seconds" icon="pi-clock" class="bg-pc-main"
                :value="stats.message_ttl_in_seconds">
      <template #value="{data}">
        <Metric v-if="data" icon="pi-clock" :value="data" unit="sec"></Metric>
      </template>
    </MetricCard>

    <MetricCard title="retention_policies" icon="pi-database" class="bg-pc-main" :value="stats.retention_policies">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
          <Metric icon="pi-clock" :value="data.retentionTimeInMinutes"
                  unit="minutes"></Metric>
          <Metric icon="pi-server" :value="data.retentionSizeInMB"
                  unit="MB"></Metric>
        </div>
      </template>
    </MetricCard>

    <MetricCard title="persistence" icon="pi-save" class="bg-pc-main" :value="stats.persistence">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
          <Metric title="E" :value="data.bookkeeperEnsemble"></Metric>
          <Metric title="Q" :value="data.bookkeeperWriteQuorum"
                  severity="warn"></Metric>
          <Metric title="A" :value="data.bookkeeperAckQuorum"
                  severity="success"></Metric>
        </div>
      </template>
    </MetricCard>


    <MetricCard title="auth_policies" icon="pi-save" class="bg-pc-main" :value="stats.auth_policies">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="backlog_quota_map" icon="pi-save" class="bg-pc-main" :value="stats.backlog_quota_map">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="clusterDispatchRate" icon="pi-save" class="bg-pc-main" :value="stats.clusterDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="topicDispatchRate" icon="pi-save" class="bg-pc-main" :value="stats.topicDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="subscriptionDispatchRate" icon="pi-save" class="bg-pc-main" :value="stats.subscriptionDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="replicatorDispatchRate" icon="pi-save" class="bg-pc-main" :value="stats.replicatorDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="clusterSubscribeRate" icon="pi-save" class="bg-pc-main" :value="stats.clusterSubscribeRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="publishMaxMessageRate" icon="pi-save" class="bg-pc-main" :value="stats.publishMaxMessageRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="latency_stats_sample_rate" icon="pi-save" class="bg-pc-main" :value="stats.latency_stats_sample_rate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>
  </div>
</template>
