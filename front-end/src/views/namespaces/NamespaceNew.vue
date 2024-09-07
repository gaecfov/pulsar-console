<script setup>
import { useNamespaceStore } from '@/stroes/useNamespaceStore';
import FormItem from '@/components/FormItem.vue';
import * as v from 'valibot';
import { useValidate } from '@/hooks/useValidate';

const dialogRef = inject('dialogRef');
const store = useNamespaceStore();
const namespace = ref();

const schema = v.string([v.trim(), v.minLength(1, 'view.namespace.error.name')]);
const { errors, submit } = useValidate();
const save = () => {
  submit(schema, namespace.value, () => {
    store.createNamespace(namespace.value).then(() => {
      dialogRef.value.close();
    });
  });
};
</script>

<template>
  <form @submit.prevent="save">
    <div class="flex flex-col gap-6">
      <FormItem title="view.namespace.name" :error="errors['default']">
        <InputText id="name" v-model="namespace" fluid></InputText>
      </FormItem>
      <div>
        <Button icon="pi pi-save" type="submit" :label="$t('action.save')"></Button>
      </div>
    </div>
  </form>
</template>
