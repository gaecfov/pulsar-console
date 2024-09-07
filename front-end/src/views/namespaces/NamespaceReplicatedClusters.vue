<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import ClusterSelect from '@/views/components/ClusterSelect.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const { policyDisplay, policyValue, postPolicy, putPolicy } = useNamespacePolicy('replication',
  p => p.replication_clusters,
  v => v ?? []);
</script>
<template>
  <PolicyArea title="view.components.clusters">
    <div class="grid grid-cols-2 gap-4">
      <PolicyItem title="view.components.replication"
                  :display="policyDisplay"
                  @save="postPolicy" @remove="putPolicy">
        <template #display="{data}">
          <Chip v-for="(item,i) in data" :key="i">{{ item }}</Chip>
        </template>
        <ClusterSelect fluid v-model="policyValue"></ClusterSelect>
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
