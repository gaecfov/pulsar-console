<script setup>
import Topics from '@/views/components/Topics.vue';
</script>
<template>
  <Card>
    <template #title>{{ $t('view.topic') }}</template>
    <template #content>
      <Topics freeMode></Topics>
    </template>
  </Card>
</template>
