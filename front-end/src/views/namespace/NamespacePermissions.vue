<script setup>
import * as ns from '@/service/NamespaceService';
import { deconstructionNamespace } from '@/util/namespace-util';
import toastUtil from '@/util/toast-util';

const dialogRef = inject('dialogRef');
const namespace = deconstructionNamespace(dialogRef.value.data.namespace);

const permissions = ref([]);
provide('namespace', namespace);
onMounted(() => {
  retrievePermissions();
});
const retrievePermissions = () => {
  ns.retrievePermissions(namespace.tenant, namespace.namespace).then(res => {
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
  ns.grantPermission(namespace.tenant, namespace.namespace, grantPermissionRequest.role,
    grantPermissionRequest.permissions).then(() => {
    toastUtil.success();
    retrievePermissions();
    permissionDialogVisible.value = false;
  });
};

const revokeAllPermissions = (role) => {
  ns.revokeAllPermissions(namespace.tenant, namespace.namespace, role).then(() => {
    toastUtil.success();
    retrievePermissions();
  });
};
</script>

<template>
  <Dialog v-model:visible="permissionDialogVisible" modal :header="$t('view.namespace.permissions.grant')"
          :style="{ width: '25rem' }">
    <form @submit.prevent="grantPermission">
      <div class="flex flex-col gap-6">
        <div>
          <label for="role" class="font-semibold w-24">{{ $t('role') }}</label>
          <InputText id="role" v-model="grantPermissionRequest.role" required fluid />
        </div>
        <div>
          <label for="permission" class="font-semibold w-24">{{ $t('permission') }}</label>
          <MultiSelect id="permission" v-model="grantPermissionRequest.permissions"
                       :options="permissionOptions" display="chip" fluid />
        </div>
        <div class="flex justify-end gap-2">
          <Button type="button" icon="pi pi-times-circle" :label="$t('cancel')"
                  severity="secondary"
                  @click="permissionDialogVisible = false"></Button>
          <Button type="submit" icon="pi pi-save" :label="$t('save')"></Button>
        </div>
      </div>
    </form>
  </Dialog>
  <div class="flex flex-col gap-4 h-full bg-pc-main p-4">
    <Toolbar>
      <template #start>
        <Button :label="$t('view.namespace.permissions.new')"
                @click="showPermissionDialog()" icon="pi pi-plus"></Button>
      </template>
    </Toolbar>
    <div class="h-full overflow-auto">
      <DataView :value="permissions" unstyled>
        <template #list="{items}">
          <div class="flex flex-col">
            <div v-for="(item, index) in items" :key="index"
                 class="flex card flex-col  sm:flex-row sm:items-center"
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
              <div class="flex md:items-end gap-4">
                <Button icon="pi pi-file-edit" outlined
                        :label="$t('view.namespace.permissions.grant')"
                        @click="showPermissionDialog(item)"></Button>
                <Button icon="pi pi-eraser" :label="$t('view.namespace.permissions.revoke')"
                        severity="warn" @click="revokeAllPermissions(item.role)"></Button>
              </div>
            </div>
          </div>
        </template>
      </DataView>
    </div>
  </div>
</template>
