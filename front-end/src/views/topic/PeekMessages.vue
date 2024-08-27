<script setup>
import { peekMessages } from '@/service/TopicService';
import JsonViewer from '@/components/JsonViewer.vue';
import { formatDate } from '@/util/formatter';

const dialogRef = inject('dialogRef');
const { topic, subscription } = dialogRef.value.data;
const num = ref(1);
const data = ref();
const dataType = computed(() => {
  if (typeof data.value === 'object') {
    return 'object';
  }
  return 'string';
});

function parsePulsarBatchMessages(data) {
  const messages = [];
  let index = 0;
  const HEADER_SIZE = 9;
  while (index < data.length) {
    index += HEADER_SIZE;
    const messageStart = index;
    let messageEnd = data.indexOf('\u0000', messageStart);
    if (messageEnd === -1) {
      messageEnd = data.length;
    }
    const messageContent = data.slice(messageStart, messageEnd);
    messages.push(messageContent);
    index = messageEnd;
  }
  return messages;
}

const batchData = computed(() => {
  if (data.value) {
    return parsePulsarBatchMessages(data.value);
  }
  return [];
});
const headers = ref();
const peek = () => {
  peekMessages(topic, subscription, num.value).then(res => {
    headers.value = res.headers;
    data.value = res.data;
  });
};
const viewMode = ref('Raw');
const viewModes = ['Raw', 'JSON'];
const toJson = (json) => {
  try {
    return JSON.parse(json);
  } catch (e) {
    return {};
  }
};
onMounted(() => {
  peek();
});
</script>

<template>
  <div class="flex flex-col gap-4">
    <Toolbar>
      <template #start>
        <div class="flex gap-2 items-center">
          {{ $t('messagePosition') }}
          <InputNumber v-model="num" :placeholder="$t('messagePosition')"></InputNumber>
          <Button :label="$t('view.topic-stats.subscription.peek')" icon="pi pi-eye"
                  @click="peek"></Button>
        </div>
      </template>
      <template #end>
        <SelectButton v-model="viewMode" :options="viewModes" aria-labelledby="basic" />
      </template>
    </Toolbar>
    <div class="flex flex-col gap-4">
      <div class="grid grid-cols-4 gap-4 p4" v-if="headers">
        <MetricCard class="bg-pc-main" title="x-pulsar-producer-name"
                    :value="headers['x-pulsar-producer-name']"></MetricCard>
        <MetricCard class="bg-pc-main" title="x-pulsar-publish-time"
                    :value="formatDate(headers['x-pulsar-publish-time'])"></MetricCard>
        <MetricCard class="bg-pc-main" title="x-pulsar-batch-size"
                    :value="headers['x-pulsar-batch-size']"></MetricCard>
        <MetricCard class="bg-pc-main" title="x-pulsar-num-batch-message"
                    :value="headers['x-pulsar-num-batch-message']"></MetricCard>
      </div>
      <div v-if="dataType==='object'">
        <JsonViewer :json="data"></JsonViewer>
      </div>
      <ScrollPanel class="h-96 p-4" v-else>
        <DataView :value="batchData">
          <template #list="{items}">
            <div class="break-words p-6" v-for="(item,i) in items" :key="i"
                 :class="{ 'border-t border-surface-200 dark:border-surface-700': i !== 0 }">
              <JsonViewer v-if="viewMode==='JSON'" :json="toJson(item)"></JsonViewer>
              <div v-if="viewMode==='Raw'">
                {{ item }}
              </div>
            </div>
          </template>
        </DataView>
      </ScrollPanel>
    </div>
  </div>
</template>
