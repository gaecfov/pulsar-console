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
import {useEmitter} from '@/hooks/useEmitter';

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
</script>
<template>
  <div class="flex flex-col gap-4">
    <NamespaceReplicatedClusters/>
    <NamespaceSubscriptionAuthentication/>
    <NamespaceCleanup/>
    <NamespaceDispatchRate/>
    <NamespaceThrottling/>
    <NamespaceBacklogQuota/>
    <NamespaceAutoTopicCreation/>
    <NamespaceInactiveTopic/>
    <NamespaceSchema/>
    <NamespaceStorage/>
    <NamespaceAntiAffinity/>
  </div>
</template>
