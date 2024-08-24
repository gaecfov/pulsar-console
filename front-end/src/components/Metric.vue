<script setup>
defineProps(['title', 'icon', 'value', 'severity', 'unit']);
</script>
<template>
  <Chip class="text-sm">
    <slot name="icon">
      <i class="pi" :class="icon" v-if="icon"></i>
    </slot>
    <slot name="title">
      <span v-if="title">{{ $t(title) }}</span>
    </slot>
    <Badge :value="value" :severity="severity"></Badge>
    <slot name="unit">
      <span v-if="unit">{{ $t(unit) }}</span>
    </slot>
  </Chip>
</template>
