<script setup>
import { useConfirm } from 'primevue/useconfirm';

const { t } = useI18n();
const props = defineProps({
  message: {
    type: String
  }
});
const emit = defineEmits(['delete', 'cancel']);
const confirm = useConfirm();
const confirmPopup = (event) => {
  confirm.require({
    target: event.currentTarget,
    message: props.message ?? t('confirm.message.delete'),
    icon: 'pi pi-info-circle',
    rejectProps: {
      label: t('cancel'),
      severity: 'secondary',
      outlined: true
    },
    acceptProps: {
      label: t('delete'),
      severity: 'danger'
    },
    accept: () => {
      emit('delete');
    },
    reject: () => {
      emit('cancel');
    }
  });
};
</script>
<template>
  <Button icon="pi pi-trash" severity="danger" outlined @click="confirmPopup($event)" />
</template>
