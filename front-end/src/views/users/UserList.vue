<script setup>
import { useDialog } from 'primevue/usedialog';
import UserForm from '@/views/users/UserForm.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useFetch } from '@/hooks/useFetch';
import * as api from '@/service/UserService';
import { useEmitter } from '@/hooks/useEmitter';
import toastUtil from '@/util/toast-util';

const dialog = useDialog();
const { t } = useI18n();
const { list, reload } = useFetch(() => {
  return api.listUsers();
});

const emitter = useEmitter();
emitter.on('user-reload', reload);

onMounted(() => {
  reload();
});

const showUserForm = () => {
  dialog.open(UserForm, {
    props: {
      header: t('view.user.form.title'),
      style: {
        width: '400px'
      }
    }
  });
};

const deleteUser = (id) => {
  api.deleteUser(id).then(() => {
    toastUtil.success();
    emitter.emit('user-reload');
    reload();
  });
};
</script>
<template>
  <Card>
    <template #title> {{ $t('view.user') }}</template>
    <template #content>
      <DataTable :value="list">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <Button :label="$t('view.user.action.new-user')" icon="pi pi-plus"
                    @click="showUserForm" />
          </div>
        </template>
        <Column field="username" :header="$t('view.user.username')"></Column>
        <Column :header="$t('view.user.role')">
          <template #body="{ data }">
            <Tag>{{ $t(data.role.toLowerCase()) }}</Tag>
          </template>
        </Column>
        <Column class="w-60">
          <template #body="{ data }">
            <ConfirmDeleteButton @confirm="deleteUser(data.id)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
