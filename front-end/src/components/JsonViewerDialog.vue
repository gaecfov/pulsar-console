<script setup>
import JsonViewer from '@/components/JsonViewer.vue';

const dialogRef = inject('dialogRef');
const json = dialogRef.value.data;
</script>

<template>
  <JsonViewer :json="json"></JsonViewer>
</template>
