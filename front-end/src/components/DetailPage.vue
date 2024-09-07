<script setup>
import GoBackButton from '@/components/GoBackButton.vue';

const props = defineProps(['title','backRoute']);
</script>
<template>
  <Card>
    <template #title>
      <div class="flex justify-between">
        <div class="flex items-center gap-2">
          <GoBackButton :route="backRoute"></GoBackButton>
          <slot name="title">
            {{ $t(title)}}
          </slot>
        </div>
        <div>
          <slot name="right"></slot>
        </div>
      </div>
    </template>
    <template #content>
      <slot></slot>
    </template>
  </Card>
</template>
