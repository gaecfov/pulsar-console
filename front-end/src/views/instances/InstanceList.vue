<script setup>
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useRouter } from 'vue-router';
import { useEmitter } from '@/hooks/useEmitter';
import { useFetch } from '@/hooks/useFetch';
import * as api from '@/service/InstanceService';
import toastUtil from '@/util/toast-util';

const emitter = useEmitter();
const { list, reload } = useFetch(() => {
  return api.listInstances();
});

onMounted(() => {
  reload();
  emitter.on('instance-reload', reload);
});

const router = useRouter();
const goDetail = (instance) => {
  if (instance) {
    router.push({ name: 'instance-detail', params: { instanceId: instance.id } });
  } else {
    router.push({ name: 'instance-new' });
  }
};

const deleteInstance = (id) => {
  api.deleteInstance(id).then(() => {
    toastUtil.success();
    emitter.emit('instance-reload');
    reload();
  });
};
</script>
<template>
  <Card>
    <template #title> {{ $t('view.instance') }}</template>
    <template #content>
      <DataTable :value="list">
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
                                   @confirm="deleteInstance(data.id)"></ConfirmDeleteButton>
            </div>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
