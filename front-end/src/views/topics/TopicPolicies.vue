<script setup>
import { deletePartitions, terminatePartitions } from '@/service/TopicService';
import toastUtil from '@/util/toast-util';
import ConfirmButton from '@/components/ConfirmButton.vue';
import TopicCleanup from '@/views/topics/TopicCleanup.vue';
import TopicBacklogQuota from '@/views/topics/TopicBacklogQuota.vue';
import TopicDispatchRate from '@/views/topics/TopicDispatchRate.vue';

const topic = inject('topic');
</script>
<template>
  <div class="flex flex-col gap-4">
    <Toolbar>
      <template #start>
        <div class="flex gap-2">
          <ConfirmButton size="small" icon="pi pi-ban"
                         @confirm="terminatePartitions(topic).then(()=>toastUtil.success())"
                         :label="$t('view.topic.action.terminate-all')"></ConfirmButton>
          <ConfirmButton size="small" icon="pi pi-trash" severity="danger"
                         @confirm="deletePartitions(topic).then(()=>toastUtil.success())"
                         :label="$t('view.topic.action.delete-all')"></ConfirmButton>
        </div>
      </template>
    </Toolbar>

    <TopicCleanup></TopicCleanup>
    <TopicBacklogQuota></TopicBacklogQuota>
    <TopicDispatchRate></TopicDispatchRate>
  </div>
</template>
