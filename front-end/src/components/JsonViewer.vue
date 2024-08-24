<script setup>
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';
const dialogRef = inject('dialogRef');
const json = dialogRef.value.data;
</script>

<template>
  <vue-json-pretty showLineNumber showIcon showSelectController :data="json" />
</template>
