<script setup>
defineProps({
  title: String,
  icon: String,
  value: {},
  editable: {
    type: Boolean,
    default: false
  },
  showClear: {
    type: Boolean,
    default: false
  }
});
const emit = defineEmits(['edit', 'clear']);
</script>
<template>
  <div v-bind="$attrs" class="card flex flex-col justify-between p-4 m-0">
    <div class="flex justify-between">
      <div class="flex-grow">
        <span class="block text-surface-500 dark:text-surface-400 font-medium mb-4">
          <slot name="title">
            {{ $t(title) }}
          </slot>
        </span>
        <div class="text-surface-700 dark:text-surface-100 font-medium text-md">
          <slot name="value" :data="value">
            <Badge v-if="value!==undefined">{{ value }}</Badge>
          </slot>
        </div>
      </div>
      <slot name="icon">
        <i v-if="icon"
           class="dark:bg-blue-400 bg-blue-200 h-8 w-8 items-center flex justify-center rounded pi"
           :class="icon"></i>
      </slot>
    </div>
    <div class="flex gap-x-2">
      <Button size="small" v-tooltip="$t('edit')" icon="pi pi-file-edit" v-if="editable" text
              @click="emit('edit')"></Button>
      <Button size="small" v-tooltip="$t('clear')" icon="pi pi-eraser" v-if="showClear" text
              @click="emit('clear')"></Button>
      <slot name="action"></slot>
    </div>
  </div>
</template>
