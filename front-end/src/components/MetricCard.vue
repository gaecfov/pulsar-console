<script setup>
import { isNull } from '@/util/assert';

const props = defineProps({
  label: String,
  title: String,
  icon: String,
  value: {},
  valueType: {
    type: String,
    default: 'number'
  },
  editable: {
    type: Boolean,
    default: false
  },
  showClear: {
    type: Boolean,
    default: false
  }
});
const emit = defineEmits(['edit', 'clear', 'submit']);

const isNeedShow = (value) => {
  return !isNull(value) && value !== '';
};
const editing = ref(false);
const editingValue = ref(props.value);

const edit = () => {
  editing.value = true;
  emit('edit');
};

const submitValue = () => {
  emit('submit', editingValue.value);
  editing.value = false;
};
const cancelEditing = () => {
  editingValue.value = props.value;
  editing.value = false;
};

watch(() => props.value, (val) => {
  editingValue.value = val;
});
</script>
<template>
  <div v-bind="$attrs" class="card flex flex-col justify-between p-4 m-0">
    <div class="flex flex-grow justify-between">
      <div class="flex-grow flex flex-col justify-between">
        <span class="block text-surface-500 dark:text-surface-400 font-medium mb-4">
          <slot name="title">
            <template v-if="title">
              {{ $t(title) }}
            </template>
            <template v-else>
              {{ label }}
            </template>
          </slot>
        </span>
        <div class="text-surface-700 dark:text-surface-100 font-medium text-md">
          <slot name="editor" v-if="editing">
            <div class="flex gap-2 items-center">

              <InputNumber v-if="valueType === 'number'" v-model="editingValue" />
              <InputText v-if="valueType === 'string'" v-model="editingValue" />
              <ToggleButton v-if="valueType === 'boolean'" v-model="editingValue" />
              <Button icon="pi pi-save" text @click="submitValue"></Button>
              <Button icon="pi pi-times-circle" text severity="warn"
                      @click="cancelEditing"></Button>
            </div>
          </slot>
          <slot name="value" v-else :data="value">
            <Badge v-if="isNeedShow(value)">{{ value }}</Badge>
          </slot>
        </div>
      </div>
      <slot name="icon">
        <i v-if="icon"
           class="dark:bg-blue-400 bg-blue-200 h-8 w-8 items-center flex justify-center rounded pi"
           :class="icon"></i>
      </slot>
    </div>
    <div class="flex gap-x-2" v-show="!editing">
      <Button size="small" v-tooltip="$t('edit')" icon="pi pi-file-edit" v-if="editable" text
              @click="edit"></Button>
      <Button size="small" v-tooltip="$t('clear')" icon="pi pi-eraser" v-if="showClear" text
              @click="emit('clear')"></Button>
      <slot name="action"></slot>
    </div>
  </div>
</template>
