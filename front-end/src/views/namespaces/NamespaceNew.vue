<script setup>
import FormItem from '@/components/FormItem.vue';
import * as v from 'valibot';
import { useValidate } from '@/hooks/useValidate';
import { createNamespace } from '@/service/NamespaceService';
import { useEmitter } from '@/hooks/useEmitter';
import toastUtil from '@/util/toast-util';

const dialogRef = inject('dialogRef');
const tenant = dialogRef.value.data.tenant;
const namespace = ref();
const emitter = useEmitter();

const schema = v.string([v.trim(), v.minLength(1, 'view.namespace.error.name')]);
const { errors, submit } = useValidate();
const save = () => {
  submit(schema, namespace.value, () => {
    createNamespace(tenant, namespace.value).then(() => {
      emitter.emit('namespace-created');
      toastUtil.success();
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
