<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyGroup from '@/views/components/PolicyGroup.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const persistence = useNamespacePolicy('persistence',
  p => p.persistence, v => v ?? {});

const encryptionRequired = useNamespacePolicy('encryptionRequired',
  p => p.encryption_required);

const deduplication = useNamespacePolicy('deduplication',
  p => p.deduplicationEnabled);

</script>
<template>
  <PolicyArea title="view.components.storage">
    <PolicyGroup title="view.components.replicationFactor">
      <PolicyItem title="view.components.persistence" clearable
                  :display="persistence.policyDisplay.value"
                  @save="persistence.postPolicy"
                  @remove="persistence.removePolicy">
        <template #display="{data}">
          <div class="w-full grid grid-cols-4 gap-4">
            <PolicyValue title="view.components.bookkeeperEnsemble"
                         :value="data.bookkeeperEnsemble"></PolicyValue>
            <PolicyValue title="view.components.bookkeeperWriteQuorum"
                         :value="data.bookkeeperWriteQuorum"></PolicyValue>
            <PolicyValue title="view.components.bookkeeperAckQuorum"
                         :value="data.bookkeeperAckQuorum"></PolicyValue>
            <PolicyValue title="view.components.managedLedgerMaxMarkDeleteRate"
                         :value="data.managedLedgerMaxMarkDeleteRate"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-4 gap-4">
          <FormItem title="view.components.bookkeeperEnsemble">
            <InputNumber v-model="persistence.policyValue.value.bookkeeperEnsemble" show-buttons
                         :suffix="$t('megabyte')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.bookkeeperWriteQuorum">
            <InputNumber v-model="persistence.policyValue.value.bookkeeperWriteQuorum"
                         show-buttons></InputNumber>
          </FormItem>
          <FormItem title="view.components.bookkeeperAckQuorum">
            <InputNumber v-model="persistence.policyValue.value.bookkeeperAckQuorum" show-buttons>
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.managedLedgerMaxMarkDeleteRate">
            <InputNumber v-model="persistence.policyValue.value.managedLedgerMaxMarkDeleteRate"
                         show-buttons></InputNumber>
          </FormItem>
        </div>
      </PolicyItem>
    </PolicyGroup>
    <div class="grid grid-cols-4 gap-4">
      <PolicyItem title="view.components.encryptionRequired" clearable
                  :display="encryptionRequired.policyDisplay.value"
                  @save="encryptionRequired.postPolicy"
                  @remove="encryptionRequired.removePolicy">
        <ToggleSwitch v-model="encryptionRequired.policyValue.value" />
      </PolicyItem>
      <PolicyItem title="view.components.deduplication" clearable
                  :display="deduplication.policyDisplay.value"
                  @save="deduplication.postPolicy"
                  @remove="deduplication.removePolicy">
        <ToggleSwitch v-model="deduplication.policyValue.value" />
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
