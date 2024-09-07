<script setup>
defineProps(['title', 'tip']);
</script>

<template>
  <div class="flex flex-col gap-2">
    <div class="flex gap-2 text-lg font-bold" v-if="title">
      {{ $t(title) }}
    </div>
    <slot></slot>
  </div>
</template>
