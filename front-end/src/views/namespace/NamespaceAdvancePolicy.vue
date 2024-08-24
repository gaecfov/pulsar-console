<script setup>
import MetricCard from '@/components/MetricCard.vue';
import Metric from '@/components/Metric.vue';

const policies = inject('namespace-policies');
const namespace = inject('namespace');


</script>

<template>
  <div class="grid grid-cols-4 gap-4">

    <MetricCard title="retention_policies" icon="pi-database" class="bg-pc-main" :value="policies.retention_policies">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
          <Metric icon="pi-clock" :value="data.retentionTimeInMinutes"
                  unit="minutes"></Metric>
          <Metric icon="pi-server" :value="data.retentionSizeInMB"
                  unit="MB"></Metric>
        </div>
      </template>
    </MetricCard>

    <MetricCard title="persistence" icon="pi-save" class="bg-pc-main" :value="policies.persistence">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
          <Metric label="E" :value="data.bookkeeperEnsemble"></Metric>
          <Metric label="Q" :value="data.bookkeeperWriteQuorum"
                  severity="warn"></Metric>
          <Metric label="A" :value="data.bookkeeperAckQuorum"
                  severity="success"></Metric>
        </div>
      </template>
    </MetricCard>


    <MetricCard title="auth_policies" icon="pi-save" class="bg-pc-main" :value="policies.auth_policies">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="backlog_quota_map" icon="pi-save" class="bg-pc-main" :value="policies.backlog_quota_map">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="clusterDispatchRate" icon="pi-save" class="bg-pc-main" :value="policies.clusterDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="topicDispatchRate" icon="pi-save" class="bg-pc-main" :value="policies.topicDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="subscriptionDispatchRate" icon="pi-save" class="bg-pc-main" :value="policies.subscriptionDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="replicatorDispatchRate" icon="pi-save" class="bg-pc-main" :value="policies.replicatorDispatchRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="clusterSubscribeRate" icon="pi-save" class="bg-pc-main" :value="policies.clusterSubscribeRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="publishMaxMessageRate" icon="pi-save" class="bg-pc-main" :value="policies.publishMaxMessageRate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>

    <MetricCard title="latency_policies_sample_rate" icon="pi-save" class="bg-pc-main" :value="policies.latency_policies_sample_rate">
      <template #value="{data}">
        <div class="flex justify-between" v-if="data">
        </div>
      </template>
    </MetricCard>
  </div>
</template>
