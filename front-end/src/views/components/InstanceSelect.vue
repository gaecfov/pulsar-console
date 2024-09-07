<script setup>
import {useInstanceStore} from '@/stroes/useInstanceStore';

const store = useInstanceStore();
const model = defineModel();

onMounted(() => {
  store.reload();
});
</script>

<template>
  <Select v-bind="$attrs" v-model="model" filter :options="store.instances" optionValue="id" optionLabel="name"
          :placeholder="$t('placeholder.select',$t('view.instance'))"/>
</template>
