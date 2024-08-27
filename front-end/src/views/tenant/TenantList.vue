<script setup>
import { useDialog } from 'primevue/usedialog';
import TenantStats from '@/views/tenant/TenantStats.vue';
import TenantForm from '@/views/tenant/TenantForm.vue';
import { useTenantStore } from '@/views/tenant/useTenantStore';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';

const { t } = useI18n();
const dialog = useDialog();
const store = useTenantStore();

onMounted(() => {
  store.reload();
});

const deleteTenant = (tenant) => {
  store.deleteTenant(tenant);
};
const showTenantForm = () => {
  dialog.open(TenantForm, {
    props: {
      header: t('view.tenant.form.title'),
      modal: true,
      style: {
        width: '400px'
      }
    }
  });
};
const showTenantStats = (tenant) => {
  dialog.open(TenantStats, {
    props: {
      header: `${tenant.tenantName}`,
      modal: true,
      style: {
        width: '500px',
        height: '400px'
      }
    },
    data: { tenant: tenant.tenantName }
  });
};
</script>
<template>
  <Card>
    <template #title>{{ $t('view.tenant.list.title') }}</template>
    <template #content>
      <DataTable :value="store.tenants">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <div>
              <Button :label="$t('view.tenant.list.new')" icon="pi pi-plus" class="mr-2" @click="showTenantForm" />
            </div>
          </div>
        </template>
        <Column :header="$t('name')">
          <template #body="{ data }">
            <Button link @click="showTenantStats(data)" :label="data.tenantName"></Button>
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
