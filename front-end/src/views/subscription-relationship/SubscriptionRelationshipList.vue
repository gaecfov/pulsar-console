<script setup>
import { deleteByTopic, saveAll, search } from '@/service/SubscriptionRelationshipService';
import { useRouter } from 'vue-router';
import { deconstructionTopic, isSystemTopic } from '@/util/topic-util';
import { useGlobalStore } from '@/stroes/useGlobalStore';
import { getSubscriptions, listTopics } from '@/service/TopicService';
import { isEmpty, isNull } from '@/util/assert';
import FormItem from '@/components/FormItem.vue';
import TenantSelect from '@/views/components/TenantSelect.vue';
import NamespaceSelect from '@/views/components/NamespaceSelect.vue';

const data = ref([]);
const pageable = reactive({
  pageSize: 10,
  page: 0,
  total: 0
});
const query = reactive({
  tenant: null,
  namespace: null,
  subscription: null,
  topic: null
});

const reload = () => {
  search({ ...query, pageSize: pageable.pageSize, page: pageable.page }).then(
    res => {
      data.value = res.data.content;
      pageable.total = res.data.totalElements;
    });
};
const pageChange = (state) => {
  pageable.page = state.page;
  pageable.pageSize = state.rows;
  reload();
};

const router = useRouter();
const goTopic = (fullTopic) => {
  const topic = deconstructionTopic(fullTopic);
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

const store = useGlobalStore();
const visible = ref(false);
const tenant = ref();
const namespace = ref();
const totalTopics = ref(0);
const processedTopics = ref(0);
const isProcessing = computed(() => {
  return totalTopics.value > 0 && totalTopics.value > processedTopics.value;
});
const isProcessingComplete = computed(() => {
  return totalTopics.value > 0 && totalTopics.value === processedTopics.value;
});
const progress = computed(() => {
  return totalTopics.value ? ((processedTopics.value / totalTopics.value) * 100).toFixed(0) : 0;
});
const showSyncDialog = () => {
  totalTopics.value = 0;
  processedTopics.value = 0;
  visible.value = true;
};
const syncSubscriptionRelationship = async () => {
  const res = await listTopics(tenant.value, namespace.value);
  const topics = res.data.filter(x => !isSystemTopic(x));
  totalTopics.value = topics.length;
  processedTopics.value = 0;
  for (const fullTopic of topics) {
    try {
      const topic = deconstructionTopic(fullTopic);
      const subscriptionsResponse = await getSubscriptions(topic);
      const subscriptions = subscriptionsResponse.data;
      if (!isEmpty(subscriptions)) {
        const subscriptionRelationships = [];
        for (const sub of subscriptions) {
          subscriptionRelationships.push({
            instanceId: store.instanceId,
            tenant: tenant.value,
            namespace: namespace.value,
            topic: topic.fullTopic,
            subscription: sub
          });
        }
        await deleteByTopic(store.instanceId, topic.fullTopic);
        await saveAll(subscriptionRelationships);
      }
      processedTopics.value++;
    } catch (topicError) {
      processedTopics.value++;
      console.error(`Error processing topic ${topic}:`, topicError);
    }
  }
};
const invalidSyncParams = computed(() => {
  return !isNull(tenant.value) && isNull(namespace.value);
});
</script>
<template>
  <Dialog v-model:visible="visible" modal :style="{ width: '25rem' }"
          :header="$t('view.subscription-relationship.action.sync')">
    <div class="flex flex-col gap-4 py-4">
      <div v-if="isProcessing || isProcessingComplete">
        <ProgressSpinner v-if="isProcessing" style="width: 50px; height: 50px" strokeWidth="8"
                         fill="transparent"></ProgressSpinner>
        <Message v-else severity="success">{{ $t(
          'view.subscription-relationship.sync-complete') }}
        </Message>
        <span class="text-2xl text-primary">{{ processedTopics }}</span>/
        <span class="text-2xl text-muted-color">{{ totalTopics }}</span>
      </div>
      <template v-else>
        <FormItem title="view.subscription-relationship.tenant">
          <TenantSelect v-model="tenant" class="w-60"></TenantSelect>
        </FormItem>
        <FormItem title="view.subscription-relationship.namespace">
          <NamespaceSelect :tenant="tenant" v-model="namespace" class="w-60"></NamespaceSelect>
        </FormItem>
      </template>
      <div class="flex justify-end gap-2 mt-8">
        <Button icon="pi pi-times-circle" type="button" :label="$t('action.cancel')"
                severity="secondary"
                @click="visible = false"></Button>
        <Button icon="pi pi-check" v-if="isProcessingComplete" severity="success"
                :label="$t('success')"
                @click="visible = false"></Button>
        <Button v-else icon="pi pi-sync" type="button"
                :label="$t('view.subscription-relationship.action.sync')"
                :disabled="invalidSyncParams || isProcessing"
                @click="syncSubscriptionRelationship"></Button>
      </div>
    </div>
  </Dialog>
  <Card>
    <template #title> {{ $t('view.subscription-relationship') }}</template>
    <template #content>
      <DataTable :value="data">
        <template #header>
          <div class="flex justify-between gap-4">
            <div class="flex gap-4">
              <Button :label="$t('view.subscription-relationship.action.sync')"
                      @click="showSyncDialog"></Button>
            </div>
            <div class="flex justify-end gap-4">
              <div class="flex gap-2 items-center">
                <span>{{ $t('view.subscription-relationship.tenant') }}</span>
                <TenantSelect v-model="query.tenant" class="w-40"></TenantSelect>
              </div>
              <div class="flex gap-2 items-center">
                <span>{{ $t('view.subscription-relationship.namespace') }}</span>
                <NamespaceSelect :tenant="query.tenant" v-model="query.namespace"
                                 class="w-40"></NamespaceSelect>
              </div>
              <div class="flex gap-2 items-center">
                <span>{{ $t('view.subscription-relationship.subscription') }}</span>
                <InputText v-model="query.subscription" @keydown.enter.prevent="reload"></InputText>
              </div>
              <div class="flex gap-2 items-center">
                <span>{{ $t('view.subscription-relationship.topic') }}</span>
                <InputText v-model="query.topic" @keydown.enter.prevent="reload"></InputText>
              </div>
              <Button icon="pi pi-search" :label="$t('action.search')" @click="reload"></Button>
            </div>
          </div>
        </template>
        <Column field="subscription"
                :header="$t('view.subscription-relationship.subscription')"></Column>
        <Column field="topic" :header="$t('view.subscription-relationship.topic')">
          <template #body="{data}">
            <Button
              link
              @click="goTopic(data.topic)"
              :label="data.topic"
            ></Button>
          </template>
        </Column>
      </DataTable>
      <Paginator v-model:rows="pageable.pageSize"
                 :totalRecords="pageable.total"
                 :rowsPerPageOptions="[10, 20, 30]" @page="pageChange"></Paginator>
    </template>
  </Card>
</template>
