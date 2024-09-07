<script setup>
import { useDialog } from 'primevue/usedialog';
import { useTenantStore } from '@/stroes/useTenantStore';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useEmitter } from '@/hooks/useEmitter';
import { useRouter } from 'vue-router';

const { t } = useI18n();
const dialog = useDialog();
const store = useTenantStore();
const emitter = useEmitter();
const router = useRouter();

onActivated(() => {
  store.reload();
  emitter.on('instance-changed', store.reload);
});

onDeactivated(() => {
  emitter.off('instance-changed');
});

const deleteTenant = (tenant) => {
  store.deleteTenant(tenant);
};

const goDetail = (tenant) => {
  router.push({
    name: 'tenant-detail',
    params: { tenant: tenant.tenantName }
  });
};
const goNew = () => {
  router.push({
    name: 'tenant-new',
  });
};
</script>
<template>
  <Card>
    <template #title>{{ $t('view.tenant') }}</template>
    <template #content>
      <DataTable :value="store.tenants">
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
            <ConfirmDeleteButton @delete="deleteTenant(data.tenantName)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
