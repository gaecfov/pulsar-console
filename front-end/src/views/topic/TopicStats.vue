<script setup>
import { getTopicStats } from '@/service/TopicService';
import TopicBaseInfo from '@/views/topic/TopicBaseInfo.vue';
import TopicPublishers from '@/views/topic/TopicPublishers.vue';
import TopicSubscriptions from '@/views/topic/TopicSubscriptions.vue';
import SimplePublishers from '@/views/topic/SimplePublishers.vue';
import SimpleSubscriptions from '@/views/topic/SimpleSubscriptions.vue';
import TopicPolicy from '@/views/topic/TopicPolicy.vue';
import { useDialog } from 'primevue/usedialog';
import JsonViewerDialog from '@/components/JsonViewerDialog.vue';

const dialogRef = inject('dialogRef');
const topic = dialogRef.value.data.topic;
const stats = ref({});
provide('topic-stats', stats);
provide('topic', topic);
let intervalId;

const loadTopicStats = () => {
  getTopicStats(topic).then((res) => {
    stats.value = res.data;
  });
};

const autoRefresh = ref(true);
const autoRefreshSeconds = ref(5);
onMounted(() => {
  loadTopicStats();
  if (autoRefresh.value) {
    intervalId = setInterval(loadTopicStats, 1000 * autoRefreshSeconds.value);
  }
});
onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId);
  }
});

const changeAutoRefresh = () => {
  if (intervalId) {
    clearInterval(intervalId);
  }
  if (autoRefresh.value) {
    intervalId = setInterval(loadTopicStats, 1000 * autoRefreshSeconds.value);
  }
};

const dialog = useDialog();
const showJson = () => {
  dialog.open(JsonViewerDialog, {
    props: {
      header: 'JSON',
      modal: true,
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: stats
  });
};
</script>

<template>
  <div class="h-full bg-pc-main p-4 overflow-auto">
    <Tabs value="topic">
      <div class="flex justify-between">
        <TabList>
          <Tab value="topic">{{ $t('view.topic-stats.topic') }}</Tab>
          <Tab value="policy">{{ $t('view.topic-stats.policy') }}</Tab>
          <Tab value="publishers">{{ $t('view.topic-stats.publishers') }}</Tab>
          <Tab value="subscriptions">{{ $t('view.topic-stats.subscriptions') }}</Tab>
        </TabList>
        <div class="flex items-center gap-2">
          <label for="auto-refresh">{{ $t('view.topic.stats.auto-refresh') }}</label>
          <ToggleSwitch id="auto-refresh" v-model="autoRefresh" @change="changeAutoRefresh" />
          <Select v-if="autoRefresh" v-model="autoRefreshSeconds" :options="[1, 5, 10, 30]"
                  @change="changeAutoRefresh" class="w-24">
            <template #option="{ option }"> {{ option }}s</template>
          </Select>
          <Button v-else icon="pi pi-refresh" @click="loadTopicStats"></Button>
          <Button icon="pi pi-code" @click="showJson"></Button>
        </div>
      </div>
      <TabPanels>
        <TabPanel value="topic" class="flex flex-col gap-4">
          <TopicBaseInfo></TopicBaseInfo>
          <SimplePublishers></SimplePublishers>
          <SimpleSubscriptions></SimpleSubscriptions>
        </TabPanel>
        <TabPanel value="policy">
          <TopicPolicy></TopicPolicy>
        </TabPanel>
        <TabPanel value="publishers">
          <TopicPublishers></TopicPublishers>
        </TabPanel>
        <TabPanel value="subscriptions">
          <TopicSubscriptions></TopicSubscriptions>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>
</template>
