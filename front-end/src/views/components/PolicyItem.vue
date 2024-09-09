<script setup>
import ConfirmButton from '@/components/ConfirmButton.vue';

defineProps({
  title: {
    type: String
  },
  display: {
    required: true
  },
  inline: {
    type: Boolean,
    default: true
  },
  clearable: {
    type: Boolean,
    default: false
  },
  unit: {
    type: String
  }
});
const emit = defineEmits(['save', 'remove']);

const onSave = (closeCallback) => {
  emit('save', closeCallback);
};
const onRemove = (closeCallback) => {
  emit('remove', closeCallback);
};
</script>

<template>
  <div class="flex flex-col gap-2 mb-2">
    <div class="text-sm font-bold text-muted-color" v-if="title">
      {{ $t(title) }}
    </div>
    <Inplace :displayProps="{style:{display:'block'}}">
      <template #display>
        <div class="flex gap-2">
          <template v-if="display!==undefined">
            <slot name="display" :data="display">
              {{ display }}
              <template v-if="unit">
                {{ $t(unit) }}
              </template>
            </slot>
          </template>
          <template v-else>
            <slot name="empty">
              {{ $t('none') }}
            </slot>
          </template>
        </div>
      </template>
      <template #content="{ closeCallback }">
        <div class="flex flex-col gap-2">
          <div>
            <slot></slot>
          </div>
          <div class="flex gap-2">
            <Button icon="pi pi-save" size="small" :label="$t('action.save')"
                    @click="onSave(closeCallback)" />
            <Button icon="pi pi-times" size="small" :label="$t('action.cancel')"
                    severity="secondary" @click="closeCallback" />
            <ConfirmButton v-if="clearable" size="small" icon="pi pi-trash"
                           :label="$t('action.remove')"
                           @confirm="onRemove(closeCallback)"></ConfirmButton>
          </div>
        </div>
      </template>
    </Inplace>
  </div>
</template>
