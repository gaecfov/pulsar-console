<script setup>
const props = defineProps(['title', 'invalid', 'error']);
const showError = computed(() => {
  return props.invalid || props.error;
});

const errors = computed(() => {
  if (Array.isArray(props.error)) {
    return props.error;
  }
  return [props.error];
});
</script>
<template>
  <div class="flex flex-col gap-2">
    <div class="font-bold">{{ $t(title) }}</div>
    <div class="flex flex-grow items-center">
      <slot>
      </slot>
    </div>
    <small v-if="showError" v-for="(e,i) in errors" :key="i" class="text-red-500">
      {{ $t(e) }}
    </small>
  </div>
</template>
