<script setup>
import {getTopicStats, listTopics} from '@/service/TopicService';
import {analyseTopic, deconstructionNamespace} from '@/util/namespace-util';
import {deconstructionTopic, isSystemTopic} from '@/util/topic-util';
import {FilterMatchMode} from '@primevue/core/api';
import {formatStorageSize} from '@/util/formatter';

const dialogRef = inject('dialogRef');
const namespace = deconstructionNamespace(dialogRef.value.data.namespace);
const loading = ref(false);
const backlogList = ref([]);
const storageSizeList = ref([]);
onMounted(() => {
  loading.value = true;
  listTopics(namespace.tenant, namespace.namespace)
    .then((res) => {
      const requests = [];
      const topics = [];
      res.data.forEach((fullTopic) => {
        if (!isSystemTopic(fullTopic)) {
          const topic = deconstructionTopic(fullTopic);
          topics.push(topic);
          requests.push(getTopicStats(topic));
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
          if (result.backlog) {
            backlogBucket.push(result.backlog);
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
});
const filters = ref({
  global: {value: null, matchMode: FilterMatchMode.CONTAINS}
});
</script>

<template>
  <Tabs value="backlog">
    <TabList>
      <Tab value="backlog">{{ $t('backlog') }}</Tab>
      <Tab value="storageSize">{{ $t('storageSize') }}</Tab>
    </TabList>
    <TabPanels>
      <TabPanel value="backlog">
        <DataTable :value="backlogList" :loading="loading" size="small" v-model:filters="filters"
                   :globalFilterFields="['topic', 'subscription']" sortField="backlog"
                   :sortOrder="-1"
                   paginator :rows="10">
          <template #header>
            <div class="flex justify-end">
              <IconField>
                <InputIcon>
                  <i class="pi pi-search"/>
                </InputIcon>
                <InputText v-model="filters['global'].value"
                           :placeholder="$t('common.keywords.search')"/>
              </IconField>
            </div>
          </template>
          <Column field="topic.topicName" :header="$t('topic')"></Column>
          <Column field="subscription" :header="$t('subscription')"></Column>
          <Column field="backlog" :header="$t('backlog')" :sortable="true"></Column>
        </DataTable>
      </TabPanel>
      <TabPanel value="storageSize">
        <DataTable :value="storageSizeList" :loading="loading" size="small"
                   v-model:filters="filters"
                   :globalFilterFields="['topic']" sortField="storageSize" :sortOrder="-1"
                   paginator :rows="10">
          <template #header>
            <div class="flex justify-end">
              <IconField>
                <InputIcon>
                  <i class="pi pi-search"/>
                </InputIcon>
                <InputText v-model="filters['global'].value"
                           :placeholder="$t('common.keywords.search')"/>
              </IconField>
            </div>
          </template>
          <Column field="topic.topicName" :header="$t('topic')"></Column>
          <Column field="storageSize" :header="$t('storageSize')" :sortable="true">
            <template #body="{data}">
              {{ formatStorageSize(data.storageSize) }}
            </template>
          </Column>
        </DataTable>
      </TabPanel>
    </TabPanels>
  </Tabs>

</template>
