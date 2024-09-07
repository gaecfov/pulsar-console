<script setup>
import { useDialog } from 'primevue/usedialog';
import { useUserStore } from '@/stroes/useUserStore';
import UserForm from '@/views/users/UserForm.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';

const dialog = useDialog();
const { t } = useI18n();
const store = useUserStore();
onMounted(() => {
  store.reload();
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
</script>
<template>
  <Card>
    <template #title> {{ $t('view.user') }}</template>
    <template #content>
      <DataTable :value="store.users" :loading="store.loading">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <div>
              <Button :label="$t('action.new-user')" icon="pi pi-plus" severity="secondary" class="mr-2" @click="showUserForm" />
            </div>
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
            <ConfirmDeleteButton @delete="store.deleteUser(data.id)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
