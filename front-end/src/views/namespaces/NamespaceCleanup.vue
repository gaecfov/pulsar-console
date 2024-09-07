<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyGroup from '@/views/components/PolicyGroup.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const messageTTL = useNamespacePolicy('messageTTL', p => p.message_ttl_in_seconds);
const retention = useNamespacePolicy('retention', p => p.retention_policies, v => {
  return v ? v : {
    retentionSizeInMB: -1,
    retentionTimeInMinutes: -1
  };
});
const compactionThreshold = useNamespacePolicy('compactionThreshold', p => p.compaction_threshold);
const offloadThreshold = useNamespacePolicy('offloadThreshold', p => p.offload_threshold);
const offloadThresholdInSeconds = useNamespacePolicy('offloadThresholdInSeconds',
  p => p.offload_threshold_in_seconds);
const offloadDeletionLagMs = useNamespacePolicy('offloadDeletionLagMs',
  p => p.offload_deletion_lag_ms);

</script>
<template>
  <PolicyArea title="view.components.cleanup">
    <div class="grid grid-cols-4 gap-4">
      <PolicyItem title="view.components.messageTTL" clearable
                  :display="messageTTL.policyDisplay.value" unit="sec"
                  @save="messageTTL.postPolicy"
                  @remove="messageTTL.removePolicy">
        <InputNumber v-model="messageTTL.policyValue.value" show-buttons></InputNumber>
      </PolicyItem>
      <PolicyItem title="view.components.compactionThreshold" clearable
                  :display="compactionThreshold.policyDisplay.value"
                  @save="compactionThreshold.putPolicy"
                  @remove="compactionThreshold.removePolicy">
        <InputNumber v-model="compactionThreshold.policyValue.value" show-buttons></InputNumber>
      </PolicyItem>
      <PolicyItem class="col-span-2" title="view.components.retention" clearable
                  :display="retention.policyDisplay.value"
                  @save="retention.postPolicy"
                  @remove="retention.removePolicy">
        <template #display="{data}">
          <div class="w-full grid grid-cols-2 gap-4">
            <PolicyValue title="view.components.retentionSizeInMB" unit="megabyte"
                         :value="data.retentionSizeInMB"></PolicyValue>
            <PolicyValue title="view.components.retentionTimeInMinutes" unit="minutes"
                         :value="data.retentionTimeInMinutes"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-2 gap-4">
          <FormItem title="view.components.retentionSizeInMB">
            <InputNumber v-model="retention.policyValue.value.retentionSizeInMB" show-buttons
                         :suffix="$t('megabyte')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.retentionTimeInMinutes">
            <InputNumber v-model="retention.policyValue.value.retentionTimeInMinutes" :suffix="$t('minutes')"
                         show-buttons></InputNumber>
          </FormItem>
        </div>
      </PolicyItem>
    </div>
    <PolicyGroup title="view.components.offload">
      <div class="grid grid-cols-4 gap-4">
        <PolicyItem title="view.components.offloadThreshold"
                    :display="offloadThreshold.policyDisplay.value"
                    @save="offloadThreshold.putPolicy">
          <InputNumber v-model="offloadThreshold.policyValue.value" show-buttons></InputNumber>
        </PolicyItem>
        <PolicyItem title="view.components.offloadThresholdInSeconds" unit="sec"
                    :display="offloadThresholdInSeconds.policyDisplay.value"
                    @save="offloadThresholdInSeconds.putPolicy">
          <InputNumber v-model="offloadThresholdInSeconds.policyValue.value" show-buttons></InputNumber>
        </PolicyItem>
        <PolicyItem title="view.components.offloadDeletionLagMs" clearable unit="ms"
                    :display="offloadDeletionLagMs.policyDisplay.value"
                    @save="offloadThresholdInSeconds.putPolicy">
          <InputNumber v-model="offloadDeletionLagMs.policyValue.value" show-buttons></InputNumber>
        </PolicyItem>
      </div>
    </PolicyGroup>
  </PolicyArea>
</template>
