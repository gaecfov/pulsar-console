<script setup>
import { useTopicStore } from '@/views/topic/useTopicStore';
import NamespaceSelect from '@/components/NamespaceSelect.vue';

const dialogRef = inject('dialogRef');
const store = useTopicStore();
const topic = ref('');
const tenant = ref(store.tenant);
const namespace = ref(store.namespace);
const persistence = ref('persistent');
const partitioned = ref(false);
const partitionNum = ref(1);
const persistenceOptions = ['persistent', 'non-persistent'];

const save = async () => {
  if (partitioned.value) {
    store.createPartitionedTopic(persistence.value, tenant.value, namespace.value, topic.value, partitionNum.value).then(() => {
      dialogRef.value.close();
    });
  } else {
    store.createNonPartitionedTopic(persistence.value, tenant.value, namespace.value, topic.value).then(() => {
      dialogRef.value.close();
    });
  }
};
</script>

<template>
  <form @submit.prevent="save">
    <div class="grid grid-cols-2 gap-2">
      <div>
        <label for="tenant" class="block font-bold mb-3">{{ $t('tenant') }}</label>
        <TenantSelect id="tenant" v-model="tenant" fluid required></TenantSelect>
      </div>
      <div>
        <label for="namespace" class="block font-bold mb-3">{{ $t('namespace') }}</label>
        <NamespaceSelect id="namespace" :tenant="tenant" v-model="namespace" fluid required></NamespaceSelect>
      </div>
      <div class="col-span-2">
        <label for="name" class="block font-bold mb-3">{{ $t('topic') }}</label>
        <InputText id="name" v-model="topic" fluid required></InputText>
      </div>
      <div class="col-span-2">
        <label for="persistence" class="block font-bold mb-3">{{ $t('persistence') }}</label>
        <SelectButton v-model="persistence" :options="persistenceOptions" aria-labelledby="basic" />
      </div>
      <div class="col-span-2">
        <label for="partitioned" class="block font-bold mb-3">{{ $t('view.topic.form.partitioned') }}</label>
        <ToggleSwitch v-model="partitioned" aria-labelledby="basic" />
      </div>
      <div v-if="partitioned">
        <label for="partition-num" class="block font-bold mb-3">{{ $t('view.topic.form.partition-num') }}</label>
        <InputNumber id="partition-num" :min="1" v-model="partitionNum" fluid showButtons buttonLayout="horizontal" />
      </div>
      <div class="col-span-2 mt-8">
        <Button icon="pi pi-save" type="submit" :label="$t('save')"></Button>
      </div>
    </div>
  </form>
</template>
