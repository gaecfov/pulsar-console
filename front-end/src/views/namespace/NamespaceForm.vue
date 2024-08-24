<script setup>
import { useNamespaceStore } from '@/views/namespace/useNamespaceStore';

const dialogRef = inject('dialogRef');
const store = useNamespaceStore();
const namespace = ref('');

const save = async () => {
  store.createNamespace(namespace.value).then(() => {
    dialogRef.value.close();
  });
};
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <div>
        <label for="name" class="block font-bold mb-3">{{ $t('name') }}</label>
        <InputText id="name" v-model="namespace" required fluid></InputText>
      </div>
      <div>
        <Button icon="pi pi-save" type="submit" :label="$t('save')"></Button>
      </div>
    </div>
  </form>
</template>
