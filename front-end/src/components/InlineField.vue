<script setup>
defineProps(['title', 'label']);
</script>
<template>
  <div class="flex gap-2 items-center">
    <div class="text-nowrap text-sm text-muted-color font-bold">
      <slot name="label">
        <template v-if="label">{{ label }}</template>
        <template v-else>
          {{ $t(title) }}
        </template>
      </slot>
    </div>
    <div>
      <slot></slot>
    </div>
  </div>
</template>
