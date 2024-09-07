<script setup>
defineProps(['title']);
</script>

<template>
  <Fieldset v-bind="$attrs">
    <template #legend>
      <span class="text-xl font-bold">{{$t(title)}}</span>
    </template>
    <slot></slot>
  </Fieldset>
</template>
