<script setup>
import { useEmitter } from '@/hooks/useEmitter';
import { useFetch } from '@/hooks/useFetch';
import * as api from '@/service/InstanceService';

const model = defineModel();
const emitter = useEmitter();
const { list, reload } = useFetch(() => {
  return api.listInstances();
});
onMounted(() => {
  reload();
});
emitter.on('instance-reload', reload);
</script>

<template>
  <Select v-bind="$attrs" v-model="model" filter :options="list" optionValue="id" optionLabel="name"
          :placeholder="$t('placeholder.select',$t('view.instance'))" />
</template>
