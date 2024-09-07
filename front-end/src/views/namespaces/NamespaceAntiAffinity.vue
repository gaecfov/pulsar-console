<script setup>
import PolicyItem from '@/views/components/PolicyItem.vue';
import * as ns from '@/service/NamespaceService';
import PolicyArea from '@/views/components/PolicyArea.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';
import toastUtil from '@/util/toast-util';

const {
  tenant,
  namespace,
  policeName,
  policyDisplay,
  policyValue
} = useNamespacePolicy('antiAffinity');

onActivated(() => {
  getPolicy();
});

const getPolicy = () => {
  ns.getPolicy(tenant.value, namespace.value, policeName).then(res => {
    policyDisplay.value = res.data;
    policyValue.value = res.data;
  });
};

const postPolicy = (closeCallback) => {
  ns.postPolicy(tenant.value, namespace.value, policeName, policyValue.value).then(res => {
    toastUtil.success();
    getPolicy();
    closeCallback();
  });
};

const removePolicy = (closeCallback) => {
  ns.removePolicy(tenant.value, namespace.value, policeName).then(res => {
    toastUtil.success();
    getPolicy();
    closeCallback();
  });
};

</script>
<template>
  <PolicyArea title="view.components.anti-affinity">
    <PolicyItem title="view.components.anti-affinity.group" clearable
                :display="policyDisplay" @save="postPolicy" @remove="removePolicy">
      <InputText v-model="policyValue" autofocus />
    </PolicyItem>
  </PolicyArea>
</template>
