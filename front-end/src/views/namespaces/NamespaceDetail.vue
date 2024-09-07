<script setup>

import DetailPage from '@/components/DetailPage.vue';

const route = useRoute();
const router = useRouter();
const props = defineProps(['tenant', 'namespace']);

const tabChange = (tab) => {
  router.push({
    name: tab
  });
};
</script>

<template>
  <DetailPage backRoute="namespaces">
    <template #title>
      {{ tenant }}/{{ namespace }}
    </template>
    <Tabs :value="route.name" @update:value="tabChange" class="mb-4">
      <TabList>
        <Tab value="namespace-overview">
          {{ $t('view.namespace.overview') }}
        </Tab>
        <Tab value="namespace-topics">
          {{ $t('view.namespace.topics') }}
        </Tab>
        <Tab value="namespace-policies">
          {{ $t('view.namespace.policies') }}
        </Tab>
        <Tab value="namespace-permissions">
          {{ $t('view.namespace.permissions') }}
        </Tab>
        <Tab value="namespace-analysis">
          {{ $t('view.namespace.analysis') }}
        </Tab>
      </TabList>
    </Tabs>
    <router-view v-slot="{ Component }">
      <keep-alive>
        <component :is="Component" />
      </keep-alive>
    </router-view>
  </DetailPage>
</template>
