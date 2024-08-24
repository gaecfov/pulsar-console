<script setup>
import InstanceForm from '@/views/instance/InstanceForm.vue';
import { useDialog } from 'primevue/usedialog';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useInstanceStore } from '@/views/instance/useInstanceStore';

const { t } = useI18n();
const dialog = useDialog();
const store = useInstanceStore();
onMounted(() => {
  store.reload();
});

const showInstanceForm = () => {
  dialog.open(InstanceForm, {
    props: {
      header: t('view.instance.form.title'),
      modal: true,
      style: {
        width: '400px'
      }
    }
  });
};
</script>
<template>
  <Card>
    <template #title> {{ $t('view.instance.list.title') }}</template>
    <template #content>
      <DataTable :value="store.instances" :loading="store.loading">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <div>
              <Button :label="$t('view.instance.list.new')" icon="pi pi-plus" severity="secondary" class="mr-2" @click="showInstanceForm" />
            </div>
          </div>
        </template>
        <Column field="id" header="ID"></Column>
        <Column field="name" header="Name"></Column>
        <Column field="serviceUrl" header="ServiceUrl"></Column>
        <Column field="webServiceUrl" header="WebServiceUrl"></Column>
        <Column class="w-60">
          <template #body="slotScope">
            <Button icon="pi pi-file-edit" outlined class="mr-2" />
            <ConfirmDeleteButton @delete="store.delInstance(slotScope.data.id)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
