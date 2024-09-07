<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const {
  policyDisplay,
  policyValue,
  postPolicy,
  removePolicy
} = useNamespacePolicy('autoTopicCreation',
  p => p.autoTopicCreationOverride,
  ((v) => {
    return v || { topicType: 'non-partitioned' };
  }));

const topicTypes = ['non-partitioned', 'partitioned'];

const changeDefaultNumPartitions = (topicType) => {
  if (topicType !== 'partitioned') {
    policyValue.defaultNumPartitions = null;
  }
};
</script>
<template>
  <PolicyArea title="view.components.autoTopicCreation">
    <PolicyItem :display="policyDisplay" clearable
                @save="postPolicy" @remove="removePolicy">
      <template #display="{data}">
        <div class="w-full grid grid-cols-4 gap-4">
          <PolicyValue title="view.components.allowAutoTopicCreation"
                       :value="data.allowAutoTopicCreation"></PolicyValue>
          <PolicyValue title="view.components.topicType"
                       :value="data.topicType"></PolicyValue>
          <PolicyValue title="view.components.defaultNumPartitions"
                       :value="data.defaultNumPartitions"></PolicyValue>
        </div>
      </template>
      <div class="grid grid-cols-4 gap-4">
        <FormItem title="view.components.allowAutoTopicCreation">
          <ToggleSwitch
            v-model="policyValue.allowAutoTopicCreation"></ToggleSwitch>
        </FormItem>
        <FormItem title="view.components.topicType">
          <SelectButton v-model="policyValue.topicType" :options="topicTypes"
                        @change="changeDefaultNumPartitions"></SelectButton>
        </FormItem>
        <FormItem title="view.components.defaultNumPartitions"
                  v-if="policyValue.topicType==='partitioned'">
          <InputNumber v-model="policyValue.defaultNumPartitions" min="1"
                       show-buttons></InputNumber>
        </FormItem>
      </div>
    </PolicyItem>
  </PolicyArea>
</template>
