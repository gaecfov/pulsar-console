<script setup>
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';

const props = defineProps(['json']);
const json = ref(props.json);
watch(() => props.json, val => json.value = val);
</script>

<template>
  <vue-json-pretty showLineNumber showIcon showSelectController :data="json" />
</template>
