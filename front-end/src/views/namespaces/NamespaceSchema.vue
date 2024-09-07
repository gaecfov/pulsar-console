<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const schemaValidationEnforced = useNamespacePolicy('schemaValidationEnforced',
  p => p.schema_validation_enforced);
const isAllowAutoUpdateSchema = useNamespacePolicy('isAllowAutoUpdateSchema',
  p => p.is_allow_auto_update_schema);

</script>
<template>
  <PolicyArea title="view.components.schema">
    <div class="grid grid-cols-4 gap-4">
      <PolicyItem title="view.components.schemaValidationEnforced"
                  :display="schemaValidationEnforced.policyDisplay.value"
                  @save="schemaValidationEnforced.postPolicy"
                  @remove="schemaValidationEnforced.removePolicy">
        <ToggleSwitch v-model="schemaValidationEnforced.policyValue.value" />
      </PolicyItem>
      <PolicyItem title="view.components.isAllowAutoUpdateSchema"
                  :display="isAllowAutoUpdateSchema.policyDisplay.value"
                  @save="isAllowAutoUpdateSchema.postPolicy"
                  @remove="isAllowAutoUpdateSchema.removePolicy">
        <ToggleSwitch v-model="isAllowAutoUpdateSchema.policyValue.value" />
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
