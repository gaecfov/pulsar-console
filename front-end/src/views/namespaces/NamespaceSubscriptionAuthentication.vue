<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const { policies, policyValue, postPolicy, removePolicy } = useNamespacePolicy(
  'subscriptionAuthMode', p => p.subscription_auth_mode);
const authModes = ['None', 'Prefix', 'Token'];

</script>
<template>
  <PolicyArea title="view.components.subscriptionAuthentication">
    <div class="grid grid-cols-4 gap-4">
      <PolicyItem title="view.components.subscriptionAuthMode"
                  :display="policies.subscription_auth_mode"
                  @save="postPolicy" @remove="removePolicy">
        <Select v-model="policyValue" class="w-80" :options="authModes" />
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
