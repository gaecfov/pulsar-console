<script setup>
import { peekMessages } from '@/service/TopicService';

const dialogRef = inject('dialogRef');
const { topic, subscription } = dialogRef.value.data;
const num = ref(1);
const data = ref();
const peek = () => {
  peekMessages(topic, subscription, num.value).then(res => {
    data.value = res.data;
  });
};
</script>

<template>
  <div class="flex flex-col gap-4">
    <div class="flex justify-end gap-2">
      <InputNumber v-model="num"></InputNumber>
      <Button :label="$t('peek')" icon="pi pi-eye" @click="peek"></Button>
    </div>
    <div class="flex flex-grow p-4 bg-amber-300 overflow-auto">
      <div v-if="data">
        {{ data }}
      </div>
      <div v-else>
        <Skeleton class="mb-2"></Skeleton>
        <Skeleton width="10rem" class="mb-2"></Skeleton>
        <Skeleton width="5rem" class="mb-2"></Skeleton>
        <Skeleton height="2rem" class="mb-2"></Skeleton>
        <Skeleton width="10rem" height="4rem"></Skeleton>
        <Skeleton class="mb-2"></Skeleton>
        <Skeleton width="10rem" class="mb-2"></Skeleton>
        <Skeleton width="5rem" class="mb-2"></Skeleton>
        <Skeleton height="2rem" class="mb-2"></Skeleton>
        <Skeleton width="10rem" height="4rem"></Skeleton>
      </div>
    </div>
  </div>
</template>
