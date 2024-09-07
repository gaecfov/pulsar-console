<script setup>
import ConfirmButton from '@/components/ConfirmButton.vue';
</script>
<template>
  <ConfirmButton v-bind="$attrs" :message="$t('confirm.message.delete')" icon="pi pi-trash"
                 severity="danger"></ConfirmButton>
</template>
