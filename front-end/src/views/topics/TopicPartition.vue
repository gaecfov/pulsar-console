<script setup>

import { deconstructionTopic } from '@/util/topic-util';
import { getInternalStats, getStats } from '@/service/TopicService';
import TopicOverview from '@/views/topics/TopicOverview.vue';
import TopicPolicies from '@/views/topics/TopicPolicies.vue';
import TopicStorage from '@/views/topics/TopicStorage.vue';
import TopicPublishers from '@/views/topics/TopicPublishers.vue';
import TopicSubscriptions from '@/views/topics/TopicSubscriptions.vue';
import JsonViewer from '@/components/JsonViewer.vue';

const props = defineProps(['fullTopic']);
const topic = ref(deconstructionTopic(props.fullTopic));
const stats = ref();
const internalStats = ref();

provide('topic', topic);
provide('topic-stats', stats);
provide('topic-internal-stats', internalStats);

const reload = () => {
  getInternalStats(topic.value).then(res => {
    internalStats.value = res.data;
  });
  getStats(topic.value).then((res) => {
    stats.value = res.data;
  });
};

const autoRefresh = ref(true);
const autoRefreshSeconds = ref(10);
let intervalId;

watch(() => props.fullTopic, () => {
  topic.value = deconstructionTopic(props.fullTopic);
  reload();
});

onActivated(() => {
  if (autoRefresh.value) {
    intervalId = setInterval(reload, 1000 * autoRefreshSeconds.value);
  }
  reload();
});

onDeactivated(() => {
  if (intervalId) {
    clearInterval(intervalId);
  }
});

const changeAutoRefresh = () => {
  if (intervalId) {
    clearInterval(intervalId);
  }
  if (autoRefresh.value) {
    intervalId = setInterval(reload, 1000 * autoRefreshSeconds.value);
  }
};
const visible = ref(false);
</script>
<template>
  <Drawer v-model:visible="visible" position="right" class="!w-1/2" header="JSON">
    <Tabs value="stats">
      <TabList>
        <Tab value="stats">Stats</Tab>
        <Tab value="internal-stats">Internal Stats</Tab>
      </TabList>
      <TabPanels>
        <TabPanel value="stats">
          <JsonViewer :json="stats"></JsonViewer>
        </TabPanel>
        <TabPanel value="internal-stats">
          <JsonViewer :json="internalStats"></JsonViewer>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </Drawer>
  <Tabs value="overview">
    <TabList class="flex-grow">
      <div class="flex w-full justify-between">
        <div>
          <Tab value="overview">{{ $t('view.topic.overview') }}</Tab>
          <Tab value="storage">{{ $t('view.topic.storage') }}</Tab>
          <Tab value="publishers">{{ $t('view.topic.publishers') }}</Tab>
          <Tab value="subscriptions">{{ $t('view.topic.subscriptions') }}</Tab>
          <Tab value="policies">{{ $t('view.topic.policies') }}</Tab>
        </div>
        <div class="flex items-center gap-2">
          <label for="auto-refresh">{{ $t('view.topic.label.auto-refresh') }}</label>
          <ToggleSwitch id="auto-refresh" v-model="autoRefresh" @change="changeAutoRefresh" />
          <Select v-if="autoRefresh" v-model="autoRefreshSeconds" :options="[1, 5, 10, 30,60]"
                  @change="changeAutoRefresh" class="w-24">
            <template #option="{ option }"> {{ option }}s</template>
          </Select>
          <Button @click="reload" icon="pi pi-sync" text></Button>
          <Button @click="visible=true" icon="pi pi-code" text></Button>
        </div>
      </div>
    </TabList>
    <TabPanels>
      <TabPanel value="overview">
        <TopicOverview></TopicOverview>
      </TabPanel>
      <TabPanel value="policies">
        <TopicPolicies></TopicPolicies>
      </TabPanel>
      <TabPanel value="storage">
        <TopicStorage></TopicStorage>
      </TabPanel>
      <TabPanel value="publishers">
        <TopicPublishers></TopicPublishers>
      </TabPanel>
      <TabPanel value="subscriptions">
        <TopicSubscriptions></TopicSubscriptions>
      </TabPanel>
    </TabPanels>
  </Tabs>
</template>
