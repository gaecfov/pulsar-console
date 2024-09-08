<script setup>
import * as ts from '@/service/TenantService';
import ClusterSelect from '@/views/components/ClusterSelect.vue';
import toastUtil from '@/util/toast-util';
import { useRouter } from 'vue-router';
import DetailPage from '@/components/DetailPage.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { isBlank } from '@/util/assert';
import { useEmitter } from '@/hooks/useEmitter';

const emitter = useEmitter();
const router = useRouter();
const props = defineProps(['tenant']);
const tenantName = ref(props.tenant);
const tenantConfig = ref({
  adminRoles: [],
  allowedClusters: []
});
onActivated(() => {
  if (props.tenant) {
    ts.getTenantConfig(props.tenant).then((res) => {
      tenantConfig.value = res.data;
    });
    tenantName.value = props.tenant;
  } else {
    tenantConfig.value = {
      adminRoles: [],
      allowedClusters: []
    };
    tenantName.value = null;
  }
});

const save = () => {
  if (props.tenant) {
    ts.updateTenant(props.tenant, tenantConfig.value).then(() => {
      toastUtil.success();
      emitter.emit('tenant-reload');
      router.back();
    });
  } else {
    ts.createTenant(tenantName.value, tenantConfig.value).then(() => {
      toastUtil.success();
      emitter.emit('tenant-reload');
      router.back();
    });
  }
};

const deleteTenant = () => {
  ts.deleteTenant(props.tenant).then(() => {
    toastUtil.success();
    emitter.emit('tenant-reload');
    router.back();
  });
};

const roleTemp = ref();
const addRole = () => {
  if (!isBlank(roleTemp.value)) {
    if (!tenantConfig.value.adminRoles.includes(roleTemp.value)) {
      tenantConfig.value.adminRoles.push(roleTemp.value);
      roleTemp.value = null;
    }
  }
};
</script>

<template>
  <DetailPage>
    <template #title>
      <template v-if="tenant">
        {{ tenant }}
      </template>
      <template v-else>
        {{ $t('view.tenant.action.new-tenant') }}
      </template>
    </template>
    <form @submit.prevent="save">
      <div class="flex flex-col gap-6">
        <FormItem title="view.tenant.name">
          <InputText id="name" class="w-80" :disabled="tenant!==undefined" v-model="tenantName"></InputText>
        </FormItem>
        <FormItem title="view.tenant.adminRoles">
          <div class="flex flex-col gap-2">
            <div class="flex flex-wrap gap-2 mb-2">
              <Chip v-for="item in tenantConfig.adminRoles" removable>{{ item }}</Chip>
            </div>
            <div>
              <InputGroup style="width: 20rem">
                <InputText :placeholder="$t('view.tenant.placeholder.adminRoles')" v-model="roleTemp"
                           @keydown.enter.prevent="addRole" />
                <Button icon="pi pi-plus" @click="addRole"></Button>
              </InputGroup>
            </div>
          </div>
        </FormItem>
        <FormItem title="view.tenant.allowedClusters">
          <ClusterSelect class="w-80" id="allowedClusters" v-model="tenantConfig.allowedClusters"
                         :placeholder="$t('view.tenant.placeholder.allowedClusters')"
                         multiple></ClusterSelect>
        </FormItem>
        <div class="flex gap-4">
          <Button icon="pi pi-save" type="submit" :label="$t('action.save')"></Button>
          <ConfirmDeleteButton v-if="tenant" @confirm="deleteTenant"></ConfirmDeleteButton>
        </div>
      </div>
    </form>
  </DetailPage>
</template>
