<script setup>
import { useConfirm } from 'primevue/useconfirm';

const { t } = useI18n();
const props = defineProps({
  severity: {
    default: 'warn'
  },
  confirmIcon: {
    default: 'pi pi-info-circle'
  },
  rejectSeverity: {
    default: 'secondary'
  },
  acceptSeverity: {
    default: 'warn'
  },
  message: {
    type: String
  }
});
const emit = defineEmits(['confirm', 'cancel']);
const confirm = useConfirm();
const confirmPopup = (event) => {
  confirm.require({
    target: event.currentTarget,
    message: props.message ?? t('confirm.message'),
    icon: props.confirmIcon,
    rejectProps: {
      label: t('action.cancel'),
      severity: props.rejectSeverity
    },
    acceptProps: {
      label: t('action.confirm'),
      severity: props.acceptSeverity
    },
    accept: () => {
      emit('confirm');
    },
    reject: () => {
      emit('cancel');
    }
  });
};
</script>
<template>
  <Button v-bind="$attrs" @click="confirmPopup($event)" :severity="severity" />
</template>
