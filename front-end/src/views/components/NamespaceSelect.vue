<script setup>
import { listNamespaces } from '@/service/NamespaceService';

const props = defineProps(['tenant']);
const namespaces = ref([]);
const model = defineModel();

const fetchNamespaces = () => {
  if (props.tenant) {
    listNamespaces(props.tenant).then((rsp) => {
      namespaces.value = rsp.data.map((item) => item.replace(`${props.tenant}/`, ''));
      if (!namespaces.value.find((x) => x === model.value)) {
        model.value = namespaces.value[0];
      }
    });
  } else {
    namespaces.value = [];
  }
};
onMounted(fetchNamespaces);
watch(() => props.tenant, fetchNamespaces);
</script>

<template>
  <Select v-bind="$attrs" v-model="model" :options="namespaces" filter
          :placeholder="$t('placeholder.select',$t('view.namespace'))" />
</template>
