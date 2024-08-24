<script setup>
import TopicStats from '@/views/topic/TopicStats.vue';
import TenantSelect from '@/components/TenantSelect.vue';
import NamespaceSelect from '@/components/NamespaceSelect.vue';
import TopicForm from '@/views/topic/TopicForm.vue';
import { FilterMatchMode } from '@primevue/core/api';
import { useDialog } from 'primevue/usedialog';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useTopicStore } from '@/views/topic/useTopicStore';

const { t } = useI18n();
const store = useTopicStore();
const dialog = useDialog();
const expandedRows = ref();
const lastTopics = useStorage('last-topics', []);
const showTopicStats = (topic) => {
  if (!lastTopics.value.find((x) => x.fullTopic === topic.fullTopic)) {
    if (lastTopics.value.length >= 10) {
      lastTopics.value.shift();
    }
    lastTopics.value.push(topic);
  }
  dialog.open(TopicStats, {
    props: {
      header: t('view.topic-stats.title'),
      modal: true,
      closeOnEscape: false,
      maximizable: true,
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: {
      topic: topic
    }
  });
};
const showTopicForm = () => {
  dialog.open(TopicForm, {
    props: {
      header: t('view.topic.form.title'),
      modal: true,
      style: {
        width: '400px'
      }
    }
  });
};

const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const isRetryTopic = (topic) => {
  return /-RETRY/.test(topic.topicName);
};

const isDLQTopic = (topic) => {
  return /-DLQ/.test(topic.topicName);
};

const isPartitionedTopic = (topic) => {
  return /-partition-\d+/.test(topic.topicName);
};

const hasChildren = (topic) => {
  return topic.children.length > 1;
};

const topicHistoryPopover = ref();
const toggleTopicHistoryPopover = (event) => {
  topicHistoryPopover.value.toggle(event);
};
</script>
<template>
  <Card v-if="store">
    <template #title>{{ $t('view.topic.list.title') }}</template>
    <template #content>
      <DataTable
        v-model:filters="filters"
        :global-filter-fields="['topicName']"
        paginator
        :rows="10"
        v-model:expandedRows="expandedRows"
        :value="store.topics"
      >
        <template #header>
          <div class="flex flex-col gap-2">
            <div class="flex flex-wrap gap-2 items-center justify-between">
              <div>
                <Button
                  :label="$t('view.topic.list.new')"
                  icon="pi pi-plus"
                  @click="showTopicForm"
                />
              </div>
              <div class="flex gap-x-2">
                <Chip>{{ $t('total') }} {{ store.topics.length }}</Chip>
                <IconField>
                  <InputIcon>
                    <i class="pi pi-search" />
                  </InputIcon>
                  <InputText
                    v-model="filters['global'].value"
                    :placeholder="$t('common.keywords.search')"
                  />
                </IconField>
                <TenantSelect v-model="store.tenant"></TenantSelect>
                <NamespaceSelect
                  :tenant="store.tenant"
                  v-model="store.namespace"
                ></NamespaceSelect>
                <Button
                  icon="pi pi-history"
                  severity="secondary"
                  v-tooltip="$t('history')"
                  @click="toggleTopicHistoryPopover"
                ></Button>
                <Popover
                  ref="topicHistoryPopover"
                  appendTo="body"
                >
                  <div class="flex flex-col gap-2">
                    <Button
                      style="justify-content: start"
                      v-for="(topic, index) in lastTopics"
                      :key="index"
                      size="small"
                      severity="secondary"
                      :label="topic.fullTopic"
                      @click="showTopicStats(topic)"
                    ></Button>
                  </div>
                </Popover>
              </div>
            </div>
          </div>
        </template>
        <Column
          expander
          style="width: 3rem"
        />
        <Column style="width: 5rem">
          <template #body="{ data }">
            <div class="flex gap-x-2">
              <Tag
                severity="warn"
                v-if="isRetryTopic(data)"
                >Retry</Tag
              >
              <Tag
                severity="danger"
                v-if="isDLQTopic(data)"
                >DLQ</Tag
              >
              <Tag v-if="isPartitionedTopic(data)">Partitioned</Tag>
            </div>
          </template>
        </Column>
        <Column
          field="humanTopicName"
          :header="$t('name')"
        >
          <template #body="{ data }">
            <span v-if="hasChildren(data)">{{ data.humanTopicName }}</span>
            <Button
              v-else
              link
              @click="showTopicStats(data)"
              :label="data.humanTopicName"
            ></Button>
          </template>
        </Column>
        <Column class="w-60">
          <template #body="{ data }">
            <div
              class="flex gap-x-4"
              v-if="!hasChildren(data)"
            >
              <ConfirmDeleteButton @delete="store.deleteTopic(data)"></ConfirmDeleteButton>
            </div>
          </template>
        </Column>
        <template #expansion="{ data }">
          <DataView :value="data.children">
            <template #list="{ items }">
              <div class="flex flex-col ml-8">
                <div
                  class="flex justify-between py-2"
                  v-for="(item, index) in items"
                  :key="index"
                  :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }"
                >
                  <div class="flex items-center">
                    <Button
                      link
                      @click="showTopicStats(item)"
                      :label="item.fullTopic"
                    ></Button>
                  </div>
                  <div class="flex gap-x-4 w-56">
                    <ConfirmDeleteButton @delete="store.deleteTopic(item)"></ConfirmDeleteButton>
                  </div>
                </div>
              </div>
            </template>
          </DataView>
        </template>
      </DataTable>
    </template>
  </Card>
</template>
