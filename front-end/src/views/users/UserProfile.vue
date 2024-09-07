<script setup>
import { useUserStore } from '@/stroes/useUserStore';
import { useGlobalStore } from '@/stroes/useGlobalStore';

const store = useUserStore();
const globalStore = useGlobalStore();
const oldPassword = ref();
const newPassword = ref();
const confirmPassword = ref();

const dialogRef = inject('dialogRef');
const save = () => {
  store.changePassword(globalStore.currentUser.userId, {
    oldPassword: oldPassword.value,
    newPassword: newPassword.value
  }).then(() => {
    dialogRef.value.close();
  });
};
const isValid = computed(() => {
  return newPassword.value === confirmPassword.value;
});
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <div>
        <label for="oldPassword" class="block font-bold mb-3">{{ $t(
          'view.user.old-password') }}</label>
        <Password id="oldPassword" v-model="oldPassword" toggleMask fluid required></Password>
      </div>
      <div>
        <label for="newPassword" class="block font-bold mb-3">{{ $t(
          'view.user.new-password') }}</label>
        <Password id="newPassword" v-model="newPassword" toggleMask fluid required></Password>
      </div>
      <div>
        <label for="confirmPassword" class="block font-bold mb-3">{{ $t(
          'view.user.confirm-password') }}</label>
        <Password id="confirmPassword" v-model="confirmPassword" toggleMask fluid required
                  :invalid="!isValid"></Password>
      </div>
      <div>
        <Button :disabled="!isValid" type="submit" icon="pi pi-save" :label="$t('action.save')"></Button>
      </div>
    </div>
  </form>
</template>
