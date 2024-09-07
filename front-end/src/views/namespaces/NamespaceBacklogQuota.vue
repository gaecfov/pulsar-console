<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const destinationStorage = useNamespacePolicy('backlogQuota',
  p => {
    if (p.backlog_quota_map) {
      return p.backlog_quota_map.destination_storage;
    }
    return undefined;
  },
  ((v) => {
    return v || { topicType: 'non-partitioned' };
  }), () => {
    return {
      backlogQuotaType: 'destination_storage'
    };
  });

const messageAge = useNamespacePolicy('backlogQuota',
  p => {
    if (p.backlog_quota_map) {
      return p.backlog_quota_map.message_age;
    }
    return undefined;
  },
  ((v) => {
    return v || { topicType: 'non-partitioned' };
  }), () => {
    return {
      backlogQuotaType: 'message_age'
    };
  });

const policyOptions = ['producer_request_hold', 'consumer_backlog_eviction', 'producer_exception'];

</script>
<template>
  <PolicyArea title="view.components.backlog">
    <div class="grid grid-cols-2 gap-4">
      <PolicyItem title="view.components.destinationStorage" clearable
                  :display="destinationStorage.policyDisplay.value"
                  @save="destinationStorage.postPolicy"
                  @remove="destinationStorage.removePolicy">
        <template #display="{data}">
          <div class="w-full grid grid-cols-2 gap-4">
            <PolicyValue title="view.components.limitSize" unit="megabyte"
                         :value="data.limitSize"></PolicyValue>
            <PolicyValue title="view.components.policy"
                         :value="data.policy"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-2 gap-4">
          <FormItem title="view.components.limitSize">
            <InputNumber v-model="destinationStorage.policyValue.value.limitSize" show-buttons
                         :suffix="$t('megabyte')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.policy">
            <Select v-model="destinationStorage.policyValue.value.policy" class="w-80"
                    :options="policyOptions"></Select>
          </FormItem>
        </div>
      </PolicyItem>
      <PolicyItem title="view.components.messageAge" clearable
                  :display="messageAge.policyDisplay.value"
                  @save="messageAge.postPolicy"
                  @remove="messageAge.removePolicy">
        <template #display="{data}">
          <div class="w-full grid grid-cols-2 gap-4">
            <PolicyValue title="view.components.limitTime" unit="sec"
                         :value="data.limitTime"></PolicyValue>
            <PolicyValue title="view.components.policy"
                         :value="data.policy"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-2 gap-4">
          <FormItem title="view.components.limitTime">
            <InputNumber v-model="messageAge.policyValue.value.limitTime" show-buttons
                         :suffix="$t('sec')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.policy">
            <Select v-model="messageAge.policyValue.value.policy" class="w-80"
                    :options="policyOptions"></Select>
          </FormItem>
        </div>
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
