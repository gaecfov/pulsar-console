<script setup>
import { useRouter } from 'vue-router';
import DetailPage from '@/components/DetailPage.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import toastUtil from '@/util/toast-util';
import *  as ccs from '@/service/ClientCertificateService';
import * as v from 'valibot';
import { useValidate } from '@/hooks/useValidate';

const props = defineProps(['clientCertificateId']);
const clientCertificate = ref({});
onMounted(() => {
  initForm();
});
onActivated(() => {
  initForm();
});

const certificateTypes = ref(['Secret', 'KeyPair']);
const signatureAlgorithms = ['RS256', 'RS384', 'RS512', 'PS256', 'PS384', 'PS512', 'ES256', 'ES384',
  'ES512'];
const macAlgorithms = ['HS256', 'HS384', 'HS512'];

const algorithms = computed(() => {
  if (clientCertificate.value.certificateType === 'Secret') {
    return macAlgorithms;
  } else {
    return signatureAlgorithms;
  }
});
const initForm = () => {
  if (props.clientCertificateId) {
    ccs.getClientCertificate(props.clientCertificateId).then(res => {
      clientCertificate.value = res.data;
    });
  } else {
    clientCertificate.value = {
      name: null,
      description: null,
      certificateType: 'Secret',
      algorithm: 'HS256'
    };
  }
};

const router = useRouter();
const schema = v.object({
  name: v.string([v.trim(), v.minLength(1, 'view.client-certificate.error.name')]),
  algorithm: v.string([v.trim(), v.minLength(1, 'view.client-certificate.error.algorithm')])
});

const { errors, submit } = useValidate();
const save = () => {
  submit(schema, clientCertificate.value, () => {
    if (props.clientCertificateId) {
      ccs.modifyClientCertificate(props.clientCertificateId, clientCertificate.value).then(() => {
        toastUtil.success();
        router.back();
      });
    } else {
      ccs.createClientCertificate(clientCertificate.value).then(() => {
        toastUtil.success();
        router.back();
      });
    }
  });
};

const deleteClientCertificate = () => {
  ccs.deleteClientCertificate(props.clientCertificateId).then(() => {
    toastUtil.success();
    router.back();
  });
};
</script>

<template>
  <DetailPage title="view.client-certificate.detail.title">
    <template #title>
      <template v-if="clientCertificateId">
        {{ clientCertificate.name }}
      </template>
      <template v-else>
        {{ $t('view.client-certificate.detail.title') }}
      </template>
    </template>
    <form @submit.prevent="save">
      <div class="flex flex-col gap-6">
        <FormItem title="view.client-certificate.name" :error="errors['name']">
          <InputText id="name" v-model="clientCertificate.name" class="w-80"></InputText>
        </FormItem>
        <FormItem title="view.client-certificate.description">
          <InputText id="description" v-model="clientCertificate.description" class="w-80" />
        </FormItem>
        <FormItem title="view.client-certificate.certificateType">
          <SelectButton v-model="clientCertificate.certificateType"
                        :options="certificateTypes"
                        @change="clientCertificate.algorithm=null"></SelectButton>
        </FormItem>
        <FormItem title="view.client-certificate.algorithm" :error="errors['algorithm']">
          <Select id="algorithm" class="w-80" v-model="clientCertificate.algorithm"
                  :options="algorithms" />
        </FormItem>
        <div class="flex gap-4">
          <Button type="submit" icon="pi pi-save" :label="$t('action.save')"></Button>
          <ConfirmDeleteButton v-if="clientCertificateId" :label="$t('action.delete')"
                               @confirm="deleteClientCertificate"></ConfirmDeleteButton>
        </div>
      </div>
    </form>
  </DetailPage>
</template>
