<script setup>
import TenantSelect from '@/components/TenantSelect.vue';
import { useDialog } from 'primevue/usedialog';
import { useNamespaceStore } from '@/views/namespace/useNamespaceStore';
import { FilterMatchMode } from '@primevue/core/api';
import NamespaceForm from '@/views/namespace/NamespaceForm.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { deconstructionNamespace } from '@/util/namespace-util';
import NamespaceAnalysis from '@/views/namespace/NamespaceAnalysis.vue';
import NamespacePolicies from '@/views/namespace/NamespacePolicies.vue';

const { t } = useI18n();
const dialog = useDialog();
const tenant = ref();
const store = useNamespaceStore();
watch(
  () => tenant.value,
  () => {
    store.tenant = tenant.value;
    store.reload();
  }
);

const showNamespaceForm = () => {
  dialog.open(NamespaceForm, {
    props: {
      header: t('view.namespace.form.title'),
      modal: true,
      style: {
        width: '300px',
        height: '250px'
      }
    }
  });
};

const showNamespacePolicies = (namespace) => {
  dialog.open(NamespacePolicies, {
    props: {
      header: t('view.namespace.policies.title'),
      modal: true,
      style: {
        width: '70dvw',
        height: '60dvh'
      }
    },
    data: { tenant, namespace: namespace.namespaceName }
  });
};

const showNamespaceAnalysis = (namespace) => {
  dialog.open(NamespaceAnalysis, {
    props: {
      header: `${namespace.namespaceName}`,
      modal: true,
      closeOnEscape: false,
      style: {
        width: '70dvw',
        height: '80dvh'
      }
    },
    data: { tenant, namespace: namespace.namespaceName }
  });
};
const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const deleteNamespace = (fullNamespace) => {
  const { namespace } = deconstructionNamespace(fullNamespace);
  store.deleteNamespace(namespace);
};
</script>
<template>
  <Card>
    <template #title>
      {{ $t('view.namespace.list.title') }}
    </template>
    <template #content>
      <DataTable :value="store.namespaces" v-model:filters="filters" paginator :rows="10"
                 :globalFilterFields="['namespaceName']">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <div>
              <Button :label="$t('view.namespace.list.new')" icon="pi pi-plus"
                      @click="showNamespaceForm" />
            </div>
            <div class="flex gap-x-2 items-center">
              <Chip>{{ $t('total') }} {{ store.namespaces.length }}</Chip>
              <IconField>
                <InputIcon>
                  <i class="pi pi-search" />
                </InputIcon>
                <InputText v-model="filters['global'].value"
                           :placeholder="$t('common.keywords.search')" />
              </IconField>
              <TenantSelect v-model="tenant"></TenantSelect>
            </div>
          </div>
        </template>
        <Column :header="$t('name')">
          <template #body="{ data }">
            <Button link @click="showNamespacePolicies(data)" :label="data.namespaceName"></Button>
          </template>
        </Column>
        <Column class="w-60">
          <template #body="{ data }">
            <Button icon="pi pi-chart-pie" outlined class="mr-2"
                    v-tooltip="$t('view.namespace.analysis')"
                    @click="showNamespaceAnalysis(data)" />
            <ConfirmDeleteButton
              @delete="deleteNamespace(data.namespaceName)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
