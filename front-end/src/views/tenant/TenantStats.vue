<script setup>
import { getTenantConfig, updateTenant } from '@/service/TenantService';
import ClusterSelect from '@/components/ClusterSelect.vue';
import toastUtil from '@/util/toast-util';

const dialogRef = inject('dialogRef');
const tenant = dialogRef.value.data.tenant;
const stats = ref({});
onMounted(() => {
  getTenantConfig(tenant).then((res) => {
    stats.value = res.data;
  });
});

const save = () => {
  updateTenant(tenant, stats.value).then(() => {
    toastUtil.success();
    dialogRef.value.close();
  });
};
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <div>
        <label for="adminRoles" class="block font-bold mb-3">{{ $t('adminRoles') }} </label>
        <AutoComplete id="adminRoles" :placeholder="$t('view.tenant.placeholder.admin-roles')"
                      @keydown.enter.prevent v-model="stats.adminRoles" multiple fluid
                      :typeahead="false" />
      </div>
      <div>
        <label for="allowedClusters" class="block font-bold mb-3">{{ $t('allowedClusters')
          }} </label>
        <ClusterSelect id="allowedClusters" v-model="stats.allowedClusters"
                       :placeholder="$t('view.tenant.placeholder.allowed-clusters')"
                       multiple></ClusterSelect>
      </div>
      <div>
        <Button icon="pi pi-save" type="submit" :label="$t('save')"></Button>
      </div>
    </div>
  </form>
</template>
