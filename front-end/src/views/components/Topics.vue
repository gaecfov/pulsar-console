<script setup>
import TenantSelect from '@/views/components/TenantSelect.vue';
import NamespaceSelect from '@/views/components/NamespaceSelect.vue';
import { FilterMatchMode } from '@primevue/core/api';
import { useRouter } from 'vue-router';
import * as ts from '@/service/TopicService';
import { mergeTopics } from '@/util/topic-util';

const props = defineProps({
  tenant: {},
  namespace: {},
  freeMode: {
    type: Boolean,
    default: false
  }
});
const currentTenant = ref(props.tenant);
const currentNamespace = ref(props.namespace);

onMounted(() => {
  loadTopics();
});

onActivated(() => {
  if (props.namespace) {
    if (currentNamespace.value !== props.namespace) {
      currentTenant.value = props.tenant;
      currentNamespace.value = props.namespace;
    }
  }
});

watch(() => currentNamespace.value, () => {
  loadTopics();
});

const recentTopics = useStorage('recent-topics', []);
const addToRecent = (topic) => {
  if (!recentTopics.value.find((x) => x.fullTopic === topic.fullTopic)) {
    if (recentTopics.value.length >= 10) {
      recentTopics.value.shift();
    }
    recentTopics.value.push(topic);
  }
};

const router = useRouter();
const goDetail = (topic) => {
  addToRecent(topic);
  router.push({
    name: 'topic-detail',
    params: {
      persistence: topic.persistence,
      tenant: topic.tenant,
      namespace: topic.namespace,
      topic: topic.humanTopicName
    }
  });
};
const goNew = () => {
  router.push({
    name: 'topic-new',
    query: {
      tenant: currentTenant.value,
      namespace: currentNamespace.value,
      freeMode: props.freeMode
    }
  });
};
const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const topics = ref([]);
onMounted(() => {
  loadTopics();
});
onActivated(() => {
  loadTopics();
});

const loadTopics = () => {
  if (currentTenant.value && currentNamespace.value) {
    ts.listTopics(currentTenant.value, currentNamespace.value).then(res => {
      topics.value = mergeTopics(res.data).filter(x => !x.system);
    });
  } else {
    topics.value = [];
  }
};

const isPartitionedTopic = (topic) => {
  return /-partition-\d+/.test(topic.topicName);
};

const topicRecentPopover = ref();
const toggleTopicRecentPopover = (event) => {
  topicRecentPopover.value.toggle(event);
};
</script>
<template>
  <DataTable
    v-model:filters="filters"
    :global-filter-fields="['topicName']"
    paginator
    :rows="10"
    :value="topics"
  >
    <template #header>
      <div class="flex flex-col gap-2">
        <div class="flex flex-wrap gap-2 items-center justify-between">
          <div>
            <Button
              :label="$t('view.topic.action.new-topic')"
              icon="pi pi-plus"
              @click="goNew"
            />
          </div>
          <div class="flex gap-x-2">
            <Chip>{{ $t('total') }} {{ topics.length }}</Chip>
            <IconField>
              <InputIcon>
                <i class="pi pi-search" />
              </InputIcon>
              <InputText
                v-model="filters['global'].value"
                :placeholder="$t('placeholder.search.keywords')"
              />
            </IconField>
            <template v-if="freeMode">
              <TenantSelect v-model="currentTenant" class="w-40"></TenantSelect>
              <NamespaceSelect
                class="w-40"
                :tenant="currentTenant"
                v-model="currentNamespace"
              ></NamespaceSelect>

              <Button
                icon="pi pi-history"
                severity="secondary"
                v-tooltip="$t('view.topic.action.recent')"
                @click="toggleTopicRecentPopover"
              ></Button>
              <Popover
                ref="topicRecentPopover"
                appendTo="body"
              >
                <div class="flex flex-col gap-2">
                  <Button
                    style="justify-content: start"
                    v-for="(topic, index) in recentTopics"
                    :key="index"
                    size="small"
                    severity="secondary"
                    :label="topic.fullTopic"
                    @click="goDetail(topic)"
                  ></Button>
                </div>
              </Popover>
            </template>
          </div>
        </div>
      </div>
    </template>
    <Column style="width: 5rem">
      <template #body="{ data }">
        <div class="flex gap-x-2">
          <Tag severity="secondary">{{ data.persistence }}</Tag>
          <Tag v-if="isPartitionedTopic(data)">partitioned</Tag>
        </div>
      </template>
    </Column>
    <Column
      field="humanTopicName"
      :header="$t('view.topic.name')"
    >
      <template #body="{ data }">
        <Button
          link
          @click="goDetail(data)"
          :label="data.humanTopicName"
        ></Button>
      </template>
    </Column>
  </DataTable>
</template>
