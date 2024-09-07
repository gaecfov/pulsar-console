<script setup>
defineProps(['label', 'title', 'icon', 'value', 'severity', 'unit', 'valueType']);
</script>
<template>
  <div class="flex items-center gap-1 text-sm">
    <slot name="icon">
      <i class="pi" :class="icon" v-if="icon"></i>
    </slot>
    <slot name="title">
      <span v-if="title">{{ $t(title) }}</span>
      <span v-else>{{ label }}</span>
    </slot>
    <ToggleSwitch v-if="valueType === 'boolean'" readonly :model-value="value" />
    <Tag v-else :severity="severity">{{ value }}</Tag>
    <slot name="unit">
      <span v-if="unit">{{ $t(unit) }}</span>
    </slot>
  </div>
</template>
