<script setup>
import { useRouter } from 'vue-router';
import DetailPage from '@/components/DetailPage.vue';
import ClientCertificateSelect from '@/views/components/ClientCertificateSelect.vue';
import * as v from 'valibot';
import { useValidate } from '@/hooks/useValidate';
import { createClientToken } from '@/service/ClientTokenService';
import toastUtil from '@/util/toast-util';

const props = defineProps(['clientTokenId']);
const clientToken = ref({});

onActivated(() => {
  clientToken.value = {
    name: null,
    description: null,
    clientCertificateId: null,
    role: null,
    expiration: null
  };
});

const router = useRouter();
const schema = v.object({
  name: v.string([v.trim(), v.minLength(1, 'view.client-token.error.name')]),
  role: v.string([v.trim(), v.minLength(1, 'view.client-token.error.role')]),
  clientCertificateId: v.nonNullable(v.nullable(v.number()),
    'view.client-token.error.clientCertificateId')
});

const { errors, submit } = useValidate();
const save = () => {
  submit(schema, clientToken.value, () => {
    createClientToken(clientToken.value).then(() => {
      toastUtil.success();
      router.back();
    });
  });
};
</script>

<template>
  <DetailPage title="view.client-token.detail.title">
    <form @submit.prevent="save">
      <div class="flex flex-col gap-6">
        <FormItem title="view.client-token.name" :error="errors['name']">
          <InputText id="name" v-model="clientToken.name" class="w-80"></InputText>
        </FormItem>
        <FormItem title="view.client-token.description">
          <InputText id="description" v-model="clientToken.description" class="w-80" />
        </FormItem>
        <FormItem title="view.client-token.role" :error="errors['role']">
          <InputText id="role" v-model="clientToken.role" class="w-80" />
        </FormItem>
        <FormItem title="view.client-token.clientCertificate"
                  :error="errors['clientCertificateId']">
          <ClientCertificateSelect class="w-80"
                                   v-model="clientToken.clientCertificateId"></ClientCertificateSelect>
        </FormItem>
        <FormItem title="view.client-token.expiration">
          <DatePicker v-model="clientToken.expiration" class="w-80"/>
        </FormItem>
        <div class="flex gap-4">
          <Button type="submit" icon="pi pi-save" :label="$t('action.save')"></Button>
        </div>
      </div>
    </form>
  </DetailPage>
</template>
