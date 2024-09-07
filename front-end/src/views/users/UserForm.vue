<script setup>
import { useUserStore } from '@/stroes/useUserStore';

const store = useUserStore();
const user = reactive({
  name: null,
  password: null,
  role: 'USER'
});

const dialogRef = inject('dialogRef');
const save = () => {
  store.createUser(user).then(() => {
    dialogRef.value.close();
  });
};
const roleOptions = ['USER', 'ADMIN'];
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <div>
        <label for="name" class="block font-bold mb-3">{{ $t('view.user.username') }}</label>
        <InputText id="name" v-model="user.username" fluid required></InputText>
      </div>

      <div>
        <label for="password" class="block font-bold mb-3">{{ $t('view.user.password') }}</label>
        <Password id="password" v-model="user.password" toggleMask fluid required></Password>
      </div>
      <div>
        <label for="role" class="block font-bold mb-3">{{ $t('view.user.role') }}</label>
        <SelectButton v-model="user.role" :options="roleOptions" aria-labelledby="basic">
          <template #option="{ option }">
            {{ $t(option.toLowerCase()) }}
          </template>
        </SelectButton>
      </div>
      <div>
        <Button type="submit" icon="pi pi-save" :label="$t('action.save')"></Button>
      </div>
    </div>
  </form>
</template>
