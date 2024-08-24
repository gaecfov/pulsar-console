<script setup>
import { listTenants } from '@/service/TenantService';
import { isNull } from '@/util/assert';

const tenants = ref([]);
const model = defineModel();

onMounted(() => {
  listTenants().then((rsp) => {
    tenants.value = rsp.data;
    if (isNull(model.value)) {
      if (tenants.value.length > 0) {
        model.value = tenants.value[0];
      }
    }
  });
});
</script>

<template>
  <Select v-bind="$attrs" v-model="model" filter :options="tenants" :placeholder="$t('select.tenant.placeholder')" />
</template>
