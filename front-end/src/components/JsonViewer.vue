<script setup>
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';

const props = defineProps(['json']);
const json = ref(props.json);
watch(() => props.json, val => json.value = val);
const { copy, isSupported } = useClipboard();
const copyJson = () => {
  copy(JSON.stringify(props.json, null, 2));
};
</script>

<template>

  <div class="flex h-full justify-between">
    <div class="overflow-auto flex-grow">
      <vue-json-pretty showLineNumber showIcon showSelectController :data="json" />
    </div>
    <div v-if="isSupported" class="flex flex-col p-2">
      <Button icon="pi pi-copy" @click="copyJson"></Button>
    </div>
  </div>

</template>
