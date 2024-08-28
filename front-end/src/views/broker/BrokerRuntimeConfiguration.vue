<script setup>
import { getRuntimeConfiguration } from '@/service/BrokerService';
import { FilterMatchMode } from '@primevue/core/api';

const runtimeConfiguration = ref([]);
onMounted(() => {
  getRuntimeConfiguration().then((res) => {
    runtimeConfiguration.value = Object.keys(res.data).map((k) => {
      return {
        name: k,
        value: res.data[k]
      };
    });
  });
});
const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});
</script>
<template>
  <DataTable :value="runtimeConfiguration" paginator :rows="10" v-model:filters="filters"
             :globalFilterFields="['name']" stripedRows resizableColumns columnResizeMode="fit">
    <template #header>
      <IconField>
        <InputIcon>
          <i class="pi pi-search" />
        </InputIcon>
        <InputText v-model="filters['global'].value" :placeholder="$t('common.keywords.search')" />
      </IconField>
    </template>
    <Column :header="$t('name')">
      <template #body="{ data }">
        <div class="flex flex-col gap-2">
          <span>{{ data.name }}</span>
          <span class="text-xs text-muted-color">{{ $t(data.name) }}</span>
        </div>
      </template>

    </Column>
    <Column field="value" :header="$t('value')"></Column>
  </DataTable>
</template>
