<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  content: {
    required: true
  },
  editable: {
    type: Boolean,
    default: false
  },
  saveValue: {
    type: Function
  }
});
const editing = ref(false);
const value = ref(props.content);
const save = async () => {
  if (props.saveValue && value.value) {
    await props.saveValue(value.value);
    editing.value = false;
  }
};
const cancel = () => {
  editing.value = false;
  value.value = props.content;
};
watch(
  () => props.content,
  (val) => {
    value.value = val;
  }
);
</script>

<template>
  <div class="flex flex-col gap-x-2">
    <div class="w-100 font-bold text-l">{{ title }}</div>
    <div class="flex items-center text-muted-color pl-4 gap-x-2" v-if="!editing">
      {{ value }}
      <Button v-if="editable" size="small" icon="pi pi-pencil" text aria-label="Star"
              @click="editing = true"></Button>
      <slot name="action"></slot>
    </div>
    <div v-else class="flex items-center">
      <InputText v-model="value"></InputText>
      <Button icon="pi pi-save" size="small" text @click="save"></Button>
      <Button icon="pi pi-times-circle" size="small" severity="secondary" text
              @click="cancel"></Button>
    </div>
  </div>
</template>
