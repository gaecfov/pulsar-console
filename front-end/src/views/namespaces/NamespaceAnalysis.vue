<script setup>
import { getStats, listTopics } from '@/service/TopicService';
import { analyseTopic } from '@/util/namespace-util';
import { deconstructionTopic, isSystemTopic } from '@/util/topic-util';
import { FilterMatchMode } from '@primevue/core/api';
import { formatStorageSize } from '@/util/formatter';
import TopicPartition from '@/views/topics/TopicPartition.vue';

const props = defineProps(['tenant', 'namespace']);

const loading = ref(false);
const backlogList = ref([]);
const storageSizeList = ref([]);

const currentTenant = ref(props.tenant);
const currentNamespace = ref(props.namespace);

const loadAnalysis = () => {
  loading.value = true;
  listTopics(currentTenant.value, currentNamespace.value)
    .then((res) => {
      const requests = [];
      const topics = [];
      res.data.forEach((fullTopic) => {
        if (!isSystemTopic(fullTopic)) {
          const topic = deconstructionTopic(fullTopic);
          topics.push(topic);
          requests.push(getStats(topic));
        }
      });
      Promise.all(requests).then((res) => {
        const backlogBucket = [];
        const storageSizeBucket = [];
        res.forEach((item, index) => {
          const result = analyseTopic(topics[index], item.data);
          if (result.storageSize) {
            storageSizeBucket.push(result.storageSize);
          }
          if (result.backlogs) {
            backlogBucket.push(...result.backlogs);
          }
        });
        backlogList.value = backlogBucket;
        storageSizeList.value = storageSizeBucket;
        loading.value = false;
      });
    })
    .catch(() => {
      loading.value = false;
    });
};
onMounted(() => {
  loadAnalysis();
});

onActivated(() => {
  if (currentNamespace.value !== props.namespace) {
    currentTenant.value = props.tenant;
    currentNamespace.value = props.namespace;
    loadAnalysis();
  }
});

const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const topicVisible = ref(false);
const currentTopic = ref();
const showTopicDetail = (topic) => {
  currentTopic.value = topic;
  topicVisible.value = true;
};

</script>
<template>
  <Dialog v-model:visible="topicVisible" modal maximizable :header="currentTopic"
          :style="{width:'80dvw',height:'80dvh'}">
    <TopicPartition v-if="currentTopic" :full-topic="currentTopic"></TopicPartition>
  </Dialog>
  <div class="flex flex-col gap-4">
    <Panel :header="$t('view.namespace.analysis.backlog')" toggleable>
      <DataTable :value="backlogList" :loading="loading" size="small" v-model:filters="filters"
                 :globalFilterFields="['topic', 'subscription']" sortField="backlog"
                 :sortOrder="-1"
                 paginator :rows="10">
        <template #header>
          <div class="flex justify-end">
            <IconField>
              <InputIcon>
                <i class="pi pi-search" />
              </InputIcon>
              <InputText v-model="filters['global'].value"
                         :placeholder="$t('placeholder.search.keywords')" />
            </IconField>
          </div>
        </template>
        <Column field="topic.topicName" :header="$t('view.namespace.analysis.topic')">
          <template #body="{data}">
            <Button link :label="data.topic.topicName"
                    @click="showTopicDetail(data.topic.fullTopic)"></Button>
          </template>
        </Column>
        <Column field="subscription" :header="$t('view.namespace.analysis.subscription')"></Column>
        <Column field="backlog" :header="$t('view.namespace.analysis.backlog')" :sortable="true"></Column>
      </DataTable>
    </Panel>
    <Panel :header="$t('view.namespace.analysis.storageSize')" toggleable>
      <DataTable :value="storageSizeList" :loading="loading" size="small"
                 v-model:filters="filters"
                 :globalFilterFields="['topic']" sortField="storageSize" :sortOrder="-1"
                 paginator :rows="10">
        <template #header>
          <div class="flex justify-end">
            <IconField>
              <InputIcon>
                <i class="pi pi-search" />
              </InputIcon>
              <InputText v-model="filters['global'].value"
                         :placeholder="$t('placeholder.search.keywords')" />
            </IconField>
          </div>
        </template>
        <Column field="topic.topicName" :header="$t('view.namespace.analysis.topic')">
          <template #body="{data}">
            <Button link @click="showTopicDetail(data.topic.fullTopic)"
                    :label="data.topic.topicName"></Button>
          </template>
        </Column>
        <Column field="storageSize" :header="$t('view.namespace.analysis.storageSize')" :sortable="true">
          <template #body="{data}">
            {{ formatStorageSize(data.storageSize) }}
          </template>
        </Column>
      </DataTable>
    </Panel>
  </div>
</template>
