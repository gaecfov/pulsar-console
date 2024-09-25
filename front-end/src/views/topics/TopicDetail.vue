<script setup>
import DetailPage from '@/components/DetailPage.vue';
import * as ts from '@/service/TopicService';
import TopicPartition from '@/views/topics/TopicPartition.vue';
import toastUtil from '@/util/toast-util';
import FormItem from '@/components/FormItem.vue';

const props = defineProps(['persistence', 'tenant', 'namespace', 'topic']);
const metadata = ref();
const currentTopic = ref();
const partitions = ref([]);
const partitionsNum = ref(0);
const loadMetadata = () => {
  ts.getMetadata({
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
      partitionsNum.value = partitions.value.length;
    } else {
      partitions.value = [];
      currentTopic.value = `${props.persistence}://${props.tenant}/${props.namespace}/${props.topic}`;
    }
  });
};
onActivated(() => {
  loadMetadata();
});

const op = ref();
const toggle = (event) => {
  op.value.toggle(event);
};
const params = reactive({
  updateLocalTopicOnly: false,
  force: false
});

const incrementPartitions = () => {
  ts.incrementPartitions({
    persistence: props.persistence,
    tenant: props.tenant,
    namespace: props.namespace,
    topicName: props.topic
  }, partitionsNum.value, params).then(() => {
    toastUtil.success();
    loadMetadata();
  });
};
</script>

<template>
  <DetailPage>
    <template #title>
      {{ persistence }}/{{ tenant }}/{{ namespace }}/{{ topic }}
    </template>
    <template #right>
      <div class="flex gap-2 text-base  items-center" v-if="partitions.length>0">
        <label for="partitions">{{ $t('view.topic.partitions') }}</label>
        <Select id="partitions" class="w-60"
                v-model="currentTopic" optionLabel="partition"
                optionValue="topic"
                :options="partitions"></Select>

        <Button type="button" icon="pi pi-plus" :label="$t('view.topic.action.increment')" @click="toggle" />
        <Popover ref="op">
          <div class="flex flex-col gap-4">
            <FormItem title="view.topic.updateLocalTopicOnly">
              <ToggleSwitch v-model="params.updateLocalTopicOnly"></ToggleSwitch>
            </FormItem>
            <FormItem title="view.topic.force">
              <ToggleSwitch v-model="params.force"></ToggleSwitch>
            </FormItem>
            <FormItem title="view.topic.partition-num">
              <InputNumber v-model="partitionsNum" show-buttons :min="partitions.length"></InputNumber>
            </FormItem>
            <div>
              <Button icon="pi pi-check" @click="incrementPartitions"
                      :label="$t('action.confirm')"></Button>
            </div>
          </div>
        </Popover>
      </div>
    </template>
    <TopicPartition v-if="currentTopic" :full-topic="currentTopic"></TopicPartition>
  </DetailPage>
</template>
