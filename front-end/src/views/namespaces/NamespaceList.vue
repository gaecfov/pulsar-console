<script setup>
import TenantSelect from '@/views/components/TenantSelect.vue';
import { useDialog } from 'primevue/usedialog';
import { FilterMatchMode } from '@primevue/core/api';
import NamespaceNew from '@/views/namespaces/NamespaceNew.vue';
import { deconstructionNamespace } from '@/util/namespace-util';
import { useRouter } from 'vue-router';
import { useEmitter } from '@/hooks/useEmitter';
import * as ns from '@/service/NamespaceService';
import { useI18n } from 'vue-i18n';
import { useFetch } from '@/hooks/useFetch';

const { t } = useI18n();
const dialog = useDialog();
const tenant = ref();
const { list, reload } = useFetch(() => {
  return ns.listNamespaces(tenant.value);
}, (data) => {
  return data.map((x) => {
    return { namespaceName: x };
  });
});

watch(() => tenant.value, reload);

const emitter = useEmitter();
emitter.on('namespace-deleted', () => {
  reload();
});
emitter.on('namespace-created', () => {
  reload();
});

const showNamespaceNew = () => {
  dialog.open(NamespaceNew, {
    props: {
      header: t('view.namespace.form.title'),
      modal: true,
      style: {
        width: '300px',
        height: '250px'
      }
    },
    data: { tenant: tenant.value }
  });
};

const router = useRouter();
const goDetail = (data) => {
  router.push({
    name: 'namespace-detail',
    params: deconstructionNamespace(data.namespaceName)
  });
};

const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});
</script>
<template>
  <Card>
    <template #title>
      {{ $t('view.namespace') }}
    </template>
    <template #content>
      <DataTable :value="list" v-model:filters="filters" paginator :rows="10"
                 :globalFilterFields="['namespaceName']">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <div>
              <Button :label="$t('view.namespace.action.new-namespace')" icon="pi pi-plus"
                      @click="showNamespaceNew" />
            </div>
            <div class="flex gap-x-2 items-center">
              <Chip>{{ $t('total') }} {{ list.length }}</Chip>
              <IconField>
                <InputIcon>
                  <i class="pi pi-search" />
                </InputIcon>
                <InputText v-model="filters['global'].value"
                           :placeholder="$t('placeholder.search.keywords')" />
              </IconField>
              <TenantSelect class="w-48" v-model="tenant"></TenantSelect>
            </div>
          </div>
        </template>
        <Column :header="$t('view.namespace.name')">
          <template #body="{ data }">
            <Button link @click="goDetail(data)" :label="data.namespaceName"></Button>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
