<script setup>
defineProps(['title', 'value', 'unit']);
</script>
<template>
  <div class="flex flex-col gap-2">
    <div class="text-base font-bold">{{ $t(title) }}</div>
    <div class="flex gap-2">
      <template v-if="value===undefined">
        <slot name="empty">
          {{ $t('none') }}
        </slot>
      </template>
      <template v-else>
        <slot>
          {{ value }}
          <template v-if="unit!==undefined">
            {{ $t(unit) }}
          </template>
        </slot>
      </template>
    </div>
  </div>
</template>
