<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const inactiveTopicPolicies = useNamespacePolicy('inactiveTopicPolicies',
  p => p.inactive_topic_policies,
  v => v ?? {});

const deleteModes = ['delete_when_no_subscriptions', 'delete_when_subscriptions_caught_up'];
</script>
<template>
  <PolicyArea title="view.components.inactiveTopic">
    <PolicyItem title="view.components.inactiveTopicPolicies" clearable
                :display="inactiveTopicPolicies.policyDisplay.value"
                @save="inactiveTopicPolicies.postPolicy"
                @remove="inactiveTopicPolicies.removePolicy">
      <template #display="{data}">
        <div class="w-full grid grid-cols-4 gap-4">
          <PolicyValue title="view.components.deleteWhileInactive"
                       :value="data.deleteWhileInactive"></PolicyValue>
          <PolicyValue title="view.components.maxInactiveDurationSeconds" unit="sec"
                       :value="data.maxInactiveDurationSeconds"></PolicyValue>
          <PolicyValue title="view.components.inactiveTopicDeleteMode"
                       :value="data.inactiveTopicDeleteMode"></PolicyValue>
        </div>
      </template>
      <div class="grid grid-cols-4 gap-4">
        <FormItem title="view.components.deleteWhileInactive">
          <ToggleSwitch
            v-model="inactiveTopicPolicies.policyValue.value.deleteWhileInactive"></ToggleSwitch>
        </FormItem>
        <FormItem title="view.components.maxInactiveDurationSeconds">
          <InputNumber v-model="inactiveTopicPolicies.policyValue.value.maxInactiveDurationSeconds"
                       :suffix="$t('sec')"
                       show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.inactiveTopicDeleteMode">
          <Select v-model="inactiveTopicPolicies.policyValue.value.inactiveTopicDeleteMode"
                  class="w-80"
                  :options="deleteModes"></Select>
        </FormItem>
      </div>
    </PolicyItem>
  </PolicyArea>
</template>
