<script setup>
import { useInstanceStore } from '@/views/instance/useInstanceStore';

const store = useInstanceStore();
const model = defineModel();

onMounted(() => {
  store.reload();
});
</script>

<template>
  <Select v-bind="$attrs" v-model="model" filter :options="store.instances" optionValue="id" optionLabel="name" :placeholder="$t('select.instance.placeholder')" />
</template>
