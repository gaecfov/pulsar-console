<script setup>
import * as ns from '@/service/NamespaceService';
import toastUtil from '@/util/toast-util';
import NamespaceAntiAffinity from '@/views/namespaces/NamespaceAntiAffinity.vue';
import NamespaceDispatchRate from '@/views/namespaces/NamespaceDispatchRate.vue';
import NamespaceThrottling from '@/views/namespaces/NamespaceThrottling.vue';
import NamespaceCleanup from '@/views/namespaces/NamespaceCleanup.vue';
import NamespaceStorage from '@/views/namespaces/NamespaceStorage.vue';
import NamespaceSchema from '@/views/namespaces/NamespaceSchema.vue';
import NamespaceSubscriptionAuthentication
  from '@/views/namespaces/NamespaceSubscriptionAuthentication.vue';
import NamespaceInactiveTopic from '@/views/namespaces/NamespaceInactiveTopic.vue';
import NamespaceReplicatedClusters from '@/views/namespaces/NamespaceReplicatedClusters.vue';
import NamespaceAutoTopicCreation from '@/views/namespaces/NamespaceAutoTopicCreation.vue';
import NamespaceBacklogQuota from '@/views/namespaces/NamespaceBacklogQuota.vue';
import { useEmitter } from '@/hooks/useEmitter';

const props = defineProps(['tenant', 'namespace']);
const policies = ref({});
const _tenant = ref(props.tenant);
const _namespace = ref(props.namespace);

watchEffect(() => {
  _tenant.value = props.tenant;
  _namespace.value = props.namespace;
});
provide('namespace-policies', policies);
provide('tenant', _tenant);
provide('namespace', _namespace);

const emitter = useEmitter();
emitter.on('namespace-policy-changed', () => {
  console.log('namespace-policy-changed');
  loadPolicies();
});

onActivated(() => {
  loadPolicies();
});

const loadPolicies = () => {
  ns.getPolicies(props.tenant, props.namespace).then((res) => {
    policies.value = res.data;
  });
};

const policySetSuccess = () => {
  loadPolicies();
  toastUtil.success();
};

const backlogQuota_destination_storage = computed(() => {
  if (policies.value.backlog_quota_map) {
    return policies.value.backlog_quota_map.destination_storage;
  }
  return undefined;
});
const backlogQuota_message_age = computed(() => {
  if (policies.value.backlog_quota_map) {
    return policies.value.backlog_quota_map.message_age;
  }
  return undefined;
});

const dispatchRate = computed(() => {
  if (policies.value.topicDispatchRate) {
    const keys = Object.keys(policies.value.topicDispatchRate);
    return policies.value.topicDispatchRate[keys[0]];
  }
  return undefined;
});

const subscriptionDispatchRate = computed(() => {
  if (policies.value.subscriptionDispatchRate) {
    const keys = Object.keys(policies.value.subscriptionDispatchRate);
    return policies.value.subscriptionDispatchRate[keys[0]];
  }
  return undefined;
});

const subscribeRate = computed(() => {
  if (policies.value.clusterSubscribeRate) {
    const keys = Object.keys(policies.value.clusterSubscribeRate);
    return policies.value.clusterSubscribeRate[keys[0]];
  }
  return undefined;
});

const updateWithPutPolicy = ['compactionThreshold', 'offloadThreshold', 'offloadThresholdInSeconds',
  'offloadDeletionLagMs'];
const savePolicyValue = (closeCallback, policyName, policy, params) => {
  if (updateWithPutPolicy.includes(policyName)) {
    ns.putPolicy(props.tenant, props.namespace, policyName, policy, params).then(() => {
      policySetSuccess();
      closeCallback();
    });
  } else {
    ns.postPolicy(props.tenant, props.namespace, policyName, policy, params).then(() => {
      policySetSuccess();
      closeCallback();
    });
  }
};

const removePolicyValue = (closeCallback, policyName, params) => {
  ns.removePolicy(props.tenant, props.namespace, policyName, params).then(() => {
    policySetSuccess();
    closeCallback();
  });
};
</script>
<template>
  <div class="flex flex-col gap-4">
    <NamespaceReplicatedClusters></NamespaceReplicatedClusters>
    <NamespaceSubscriptionAuthentication :subscriptionAuthMode="policies.subscription_auth_mode"
                                         @save="savePolicyValue"
                                         @remove="removePolicyValue"></NamespaceSubscriptionAuthentication>
    <NamespaceCleanup :messageTTL="policies.message_ttl_in_seconds"
                      :retention="policies.retention_policies"
                      :compactionThreshold="policies.compaction_threshold"
                      :offloadThreshold="policies.offload_threshold"
                      :offloadThresholdInSeconds="policies.offload_threshold_in_seconds"
                      :offloadDeletionLagMs="policies.offload_deletion_lag_ms"
                      @save="savePolicyValue" @remove="removePolicyValue"
    ></NamespaceCleanup>
    <NamespaceDispatchRate :dispatchRate="dispatchRate"
                           :subscriptionDispatchRate="subscriptionDispatchRate"
                           :subscribeRate="subscribeRate"
                           @save="savePolicyValue"
                           @remove="removePolicyValue"></NamespaceDispatchRate>
    <NamespaceThrottling
      :max-consumers-per-subscription="policies.max_consumers_per_subscription"
      :max-producers-per-topic="policies.max_producers_per_topic"
      :max-topics-per-namespace="policies.max_topics_per_namespace"
      :max-unacked-messages-per-consumer="policies.max_unacked_messages_per_consumer"
      :max-unacked-messages-per-subscription="policies.max_unacked_messages_per_subscription"
      :max-consumers-per-topic="policies.max_consumers_per_topic"
      :max-subscriptions-per-topic="policies.max_subscriptions_per_topic"
      @save="savePolicyValue" @remove="removePolicyValue"></NamespaceThrottling>
    <NamespaceBacklogQuota :destination-storage="backlogQuota_destination_storage"
                           :message-age="backlogQuota_message_age"
                           @save="savePolicyValue"
                           @remove="removePolicyValue"></NamespaceBacklogQuota>
    <NamespaceAutoTopicCreation
      :autoTopicCreation="policies.autoTopicCreationOverride"
      @save="savePolicyValue" @remove="removePolicyValue"></NamespaceAutoTopicCreation>
    <NamespaceInactiveTopic
      :inactive-topic-policies="policies.inactive_topic_policies"
      @save="savePolicyValue" @remove="removePolicyValue"></NamespaceInactiveTopic>
    <NamespaceSchema
      :isAllowAutoUpdateSchema="policies.is_allow_auto_update_schema"
      :schema-validation-enforced="policies.schema_validation_enforced"
      @save="savePolicyValue" @remove="removePolicyValue"></NamespaceSchema>
    <NamespaceStorage
      :persistence="policies.persistence"
      :deduplication="policies.deduplicationEnabled"
      :encryptionRequired="policies.encryption_required"
      @save="savePolicyValue" @remove="removePolicyValue"></NamespaceStorage>
    <NamespaceAntiAffinity></NamespaceAntiAffinity>
  </div>
</template>
