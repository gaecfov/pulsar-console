<script setup>
import ClusterSelect from '@/components/ClusterSelect.vue';
import { isBlank } from '@/util/assert';
import { useTenantStore } from '@/views/tenant/useTenantStore';

const dialogRef = inject('dialogRef');
const store = useTenantStore();
const tenant = ref('');
const tenantModel = reactive({
  adminRoles: [],
  allowedClusters: []
});

const save = async () => {
  store.createTenant(tenant.value, tenantModel).then(() => {
    dialogRef.value.close();
  });
};

const removeRole = (role) => {
  const index = tenantModel.adminRoles.indexOf(role);
  tenantModel.adminRoles.splice(index, 1);
};
const role = ref('');
const newRole = (event) => {
  event.preventDefault();
  if (!isBlank(role.value)) {
    if (tenantModel.adminRoles.find((x) => x === role.value)) {
      return;
    }
    tenantModel.adminRoles.push(role.value);
    role.value = null;
  }
};
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <div>
        <label for="name" class="block font-bold mb-3">{{ $t('name') }}</label>
        <InputText id="name" v-model="tenant" fluid required></InputText>
      </div>
      <div>
        <label for="adminRoles" class="block font-bold mb-3">{{ $t('adminRoles') }} </label>
        <AutoComplete id="adminRoles" :placeholder="$t('view.tenant.placeholder.admin-roles')"
                      v-model="tenantModel.adminRoles" @keydown.enter.prevent multiple fluid
                      :typeahead="false" />
      </div>
      <div>
        <label for="allowedClusters" class="block font-bold mb-3">{{ $t('allowedClusters')
          }} </label>
        <ClusterSelect id="allowedClusters"
                       :placeholder="$t('view.tenant.placeholder.allowed-clusters')"
                       v-model="tenantModel.allowedClusters" multiple></ClusterSelect>
      </div>
      <div>
        <Button icon="pi pi-save" type="submit" :label="$t('save')"></Button>
      </div>
    </div>
  </form>
</template>
