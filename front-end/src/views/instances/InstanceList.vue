<script setup>
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useInstanceStore } from '@/stroes/useInstanceStore';
import { useRouter } from 'vue-router';

const store = useInstanceStore();
onActivated(() => {
  store.reload();
});

const router = useRouter();
const goDetail = (instance) => {
  if (instance) {
    router.push({ name: 'instance-detail', params: { instanceId: instance.id } });
  } else {
    router.push({ name: 'instance-new' });
  }
};
</script>
<template>
  <Card>
    <template #title> {{ $t('view.instance') }}</template>
    <template #content>
      <DataTable :value="store.instances" :loading="store.loading" >
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <Button :label="$t('view.instance.action.new-instance')" icon="pi pi-plus"
                    @click="goDetail()" />
          </div>
        </template>
        <Column field="id" header="ID"></Column>
        <Column field="name" :header="$t('view.instance.name')">
          <template #body="{data}">
            <Button link @click="goDetail(data)" :label="data.name"></Button>
          </template>
        </Column>
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
              <ConfirmDeleteButton v-permission="'admin'"
                                   @confirm="store.deleteInstance(data.id)"></ConfirmDeleteButton>
            </div>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
