<script setup>
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useEmitter } from '@/hooks/useEmitter';
import { useRouter } from 'vue-router';
import { useFetch } from '@/hooks/useFetch';
import * as api from '@/service/TenantService';
import toastUtil from '@/util/toast-util';

const emitter = useEmitter();
const router = useRouter();

const { list, reload } = useFetch(() => {
  return api.listTenants();
}, data => {
  return data.map((x) => {
    return { tenantName: x };
  });
});

onMounted(() => {
  reload();
});
emitter.on('tenant-reload', reload);

const deleteTenant = (tenant) => {
  api.deleteTenant(tenant).then(() => {
    toastUtil.success();
    reload();
  });
};

const goDetail = (tenant) => {
  router.push({
    name: 'tenant-detail',
    params: { tenant: tenant.tenantName }
  });
};
const goNew = () => {
  router.push({
    name: 'tenant-new'
  });
};
</script>
<template>
  <Card>
    <template #title>{{ $t('view.tenant') }}</template>
    <template #content>
      <DataTable :value="list">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <div>
              <Button :label="$t('view.tenant.action.new-tenant')" icon="pi pi-plus" class="mr-2"
                      @click="goNew" />
            </div>
          </div>
        </template>
        <Column :header="$t('view.tenant.name')">
          <template #body="{ data }">
            <Button link @click="goDetail(data)" :label="data.tenantName"></Button>
          </template>
        </Column>

        <Column class="w-60">
          <template #body="{ data }">
            <ConfirmDeleteButton @confirm="deleteTenant(data.tenantName)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
