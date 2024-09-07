<script setup>
import DetailPage from '@/components/DetailPage.vue';
import { getMetadata } from '@/service/TopicService';
import TopicPartition from '@/views/topics/TopicPartition.vue';

const props = defineProps(['persistence', 'tenant', 'namespace', 'topic']);
const metadata = ref();
const currentTopic = ref();
const partitions = ref([]);
const loadMetadata = () => {
  getMetadata({
    persistence: props.persistence,
    tenant: props.tenant,
    namespace: props.namespace,
    humanTopicName: props.topic
  }).then(res => {
    metadata.value = res.data;
    if (metadata.value && metadata.value.partitions) {
      partitions.value = Object.keys(metadata.value.partitions).map(key => {
        const index = key.lastIndexOf('partition-');
        return {
          partition: key.substring(index),
          topic: key
        };
      });
      currentTopic.value = partitions.value[0].topic;
    } else {
      partitions.value = [];
      currentTopic.value = `${props.persistence}://${props.tenant}/${props.namespace}/${props.topic}`;
    }
  });
};
onActivated(() => {
  loadMetadata();
});
</script>

<template>
  <DetailPage>
    <template #title>
      {{ persistence }}/{{ tenant }}/{{ namespace }}/{{ topic }}
    </template>
    <template #right>
      <div class="flex gap-2 text-base  items-center" v-if="partitions.length>0">
        <label for="partitions">{{ $t('partitions') }}</label>
        <Select id="partitions" class="w-60"
                v-model="currentTopic" optionLabel="partition"
                optionValue="topic"
                :options="partitions"></Select>
      </div>
    </template>
    <TopicPartition v-if="currentTopic" :full-topic="currentTopic"></TopicPartition>
  </DetailPage>
</template>
