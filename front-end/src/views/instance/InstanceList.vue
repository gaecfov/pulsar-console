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

const showInstanceForm = (instance) => {
  dialog.open(InstanceForm, {
    props: {
      header: t('view.instance.form.title'),
      modal: true,
      style: {
        width: '50dvw',
        height: '60dvw'
      }
    },
    data: instance
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
            <Button :label="$t('view.instance.list.new')" icon="pi pi-plus"
                    @click="showInstanceForm()" />
          </div>
        </template>
        <Column field="id" header="ID"></Column>
        <Column field="name" :header="$t('name')"></Column>
        <Column field="webServiceUrl" :header="$t('view.instance.webServiceUrl')"></Column>
        <Column field="authenticationEnabled" :header="$t('view.instance.authenticationEnabled')">
          <template #body="{data}">
            <ToggleSwitch v-model="data.authenticationEnabled" readonly />
          </template>
        </Column>
        <Column field="tlsEnabled" :header="$t('view.instance.tlsEnabled')">
          <template #body="{data}">
            <ToggleSwitch v-model="data.tlsEnabled" readonly />
          </template>
        </Column>
        <Column class="w-60">
          <template #body="{data}">
            <div class="flex items-center gap-4">
              <Button icon="pi pi-file-edit" outlined @click="showInstanceForm(data)" />
              <ConfirmDeleteButton v-permission="'admin'"
                                   @delete="store.deleteInstance(data.id)"></ConfirmDeleteButton>
            </div>

          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
