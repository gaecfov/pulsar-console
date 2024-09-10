<script setup>
import { listTenants } from '@/service/TenantService';
import { isNull } from '@/util/assert';
import { useEmitter } from '@/hooks/useEmitter';

const tenants = ref([]);
const model = defineModel();
const emitter = useEmitter();

emitter.on('current-instance-changed', (e) => {
  model.value = null;
  reload();
});
emitter.on('tenant-reload', (e) => {
  reload();
});

onMounted(()=>{
  reload();
})

const reload = () => {
  listTenants().then((rsp) => {
    tenants.value = rsp.data;
    if (isNull(model.value)) {
      if (tenants.value.length > 0) {
        model.value = tenants.value[0];
      }
    }
  });
};
</script>

<template>
  <Select v-bind="$attrs" v-model="model" filter :options="tenants"
          :placeholder="$t('placeholder.select',$t('view.tenant'))" />
</template>
