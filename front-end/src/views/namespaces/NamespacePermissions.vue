<script setup>
import * as ns from '@/service/NamespaceService';
import toastUtil from '@/util/toast-util';
import ConfirmButton from '@/components/ConfirmButton.vue';

const props = defineProps(['tenant', 'namespace']);
const permissions = ref([]);
onMounted(() => {
  retrievePermissions();
});
const retrievePermissions = () => {
  ns.retrievePermissions(props.tenant, props.namespace).then(res => {
    const data = [];
    if (res.data) {
      Object.keys(res.data).forEach(key => {
        data.push({
          role: key,
          permissions: res.data[key]
        });
      });
    }
    permissions.value = data;
  });
};

const permissionDialogVisible = ref(false);
const permissionOptions = ref(['produce', 'consume', 'functions', 'sources', 'sinks', 'packages']);
const grantPermissionRequest = reactive({
  role: null,
  permissions: []
});

const showPermissionDialog = (permission) => {
  if (permission) {
    grantPermissionRequest.role = permission.role;
    grantPermissionRequest.permissions = permission.permissions;
  } else {
    grantPermissionRequest.role = null;
    grantPermissionRequest.permissions = [];
  }
  permissionDialogVisible.value = true;
};
const grantPermission = () => {
  ns.grantPermission(props.tenant, props.namespace, grantPermissionRequest.role,
    grantPermissionRequest.permissions).then(() => {
    toastUtil.success();
    retrievePermissions();
    permissionDialogVisible.value = false;
  });
};

const revokeAllPermissions = (role) => {
  ns.revokeAllPermissions(props.tenant, props.namespace, role).then(() => {
    toastUtil.success();
    retrievePermissions();
  });
};
</script>

<template>
  <Dialog v-model:visible="permissionDialogVisible" modal
          :header="$t('view.namespace.permissions')"
          :style="{ width: '25rem' }">
    <form @submit.prevent="grantPermission">
      <div class="flex flex-col gap-6">
        <div>
          <label for="role" class="font-semibold w-24">{{ $t('view.namespace.permissions.role')
            }}</label>
          <InputText id="role" v-model="grantPermissionRequest.role" required fluid />
        </div>
        <div>
          <label for="permission" class="font-semibold w-24">{{ $t('view.namespace.permissions')
            }}</label>
          <MultiSelect id="permission" v-model="grantPermissionRequest.permissions"
                       :options="permissionOptions" display="chip" fluid />
        </div>
        <div class="flex justify-end gap-2">
          <Button type="button" icon="pi pi-times-circle" :label="$t('action.cancel')"
                  severity="secondary"
                  @click="permissionDialogVisible = false"></Button>
          <Button type="submit" icon="pi pi-save" :label="$t('action.save')"></Button>
        </div>
      </div>
    </form>
  </Dialog>
  <div class="flex flex-col gap-4">
    <Toolbar>
      <template #start>
        <Button :label="$t('view.namespace.action.new-permission')"
                @click="showPermissionDialog()" icon="pi pi-plus"></Button>
      </template>
    </Toolbar>
    <DataView :value="permissions" unstyled>
      <template #list="{items}">
        <div class="flex flex-col gap-4">
          <div v-for="(item, index) in items" :key="index"
               class="flex flex-col sm:flex-row sm:items-center"
               :class="{ 'border-t border-surface-200 dark:border-surface-700': index !== 0 }">
            <div class="flex flex-col flex-grow justify-between items-start gap-4">
              <div class="flex items-center">
                <i class="pi pi-user w-8 h-8 !text-2xl"></i>
                <div class="text-xl font-medium mt-2">{{ item.role }}</div>
              </div>
              <div class="flex gap-2">
                <Tag v-for="(p,i) in item.permissions" :key="i">
                  {{ p }}
                </Tag>
              </div>
            </div>
            <div class="flex gap-4">
              <Button icon="pi pi-file-edit"
                      :label="$t('view.namespace.action.grant')"
                      @click="showPermissionDialog(item)"></Button>
              <ConfirmButton icon="pi pi-eraser" :label="$t('view.namespace.action.revoke')"
                             @confirm="revokeAllPermissions(item.role)"></ConfirmButton>
            </div>
          </div>
        </div>
      </template>
    </DataView>
  </div>
</template>
