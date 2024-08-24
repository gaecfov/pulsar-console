<script setup>
import { useInstanceStore } from '@/views/instance/useInstanceStore';

const store = useInstanceStore();
const instance = reactive({
  name: null,
  webServiceUrl: null
});

const dialogRef = inject('dialogRef');
const save = () => {
  store.addInstance(instance).then(() => {
    dialogRef.value.close();
  });
};
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <div>
        <label for="name" class="block font-bold mb-3">{{ $t('name') }}</label>
        <InputText id="name" v-model="instance.name" class="w-full" required></InputText>
      </div>
      <div>
        <label for="webServiceUrl" class="block font-bold mb-3">WebServiceUrl</label>
        <InputText id="webServiceUrl" v-model="instance.webServiceUrl" class="w-full" required />
      </div>
      <div>
        <Button type="submit" icon="pi pi-save" :label="$t('save')"></Button>
      </div>
    </div>
  </form>
</template>
