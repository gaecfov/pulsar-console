<script setup>
import ClusterSelector from '@/components/ClusterSelect.vue';
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
        <label for="serviceUrl" class="block font-bold mb-3">{{ $t('adminRoles') }} </label>
        <div class="flex flex-wrap items-center gap-2">
          <Chip v-for="(item, index) in tenantModel.adminRoles" :key="index" removable
                @remove="removeRole(item)">
            {{ item }}
          </Chip>
          <InputGroup style="width: 10rem">
            <InputText v-model="role" @keydown.enter="newRole"></InputText>
            <Button icon="pi pi-plus" @click="newRole" />
          </InputGroup>
        </div>
      </div>
      <div>
        <label for="webServiceUrl" class="block font-bold mb-3">{{ $t('allowedClusters') }} </label>
        <ClusterSelector v-model="tenantModel.allowedClusters" multiple></ClusterSelector>
      </div>
      <div>
        <Button icon="pi pi-save" type="submit" :label="$t('save')"></Button>
      </div>
    </div>
  </form>
</template>
