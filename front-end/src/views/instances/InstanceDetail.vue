<script setup>
import * as ins from '@/service/InstanceService';
import { useRouter } from 'vue-router';
import DetailPage from '@/components/DetailPage.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import toastUtil from '@/util/toast-util';
import * as v from 'valibot';
import { useValidate } from '@/hooks/useValidate';
import * as api from '@/service/InstanceService';
import { useEmitter } from '@/hooks/useEmitter';

const emitter = useEmitter();
const props = defineProps(['instanceId']);
const instance = ref({});
const keyStoreType = ['JKS', 'PKCS12', 'JCEKS', 'PKCS11', 'BKS'];
onMounted(() => {
  initForm();
});
onActivated(() => {
  initForm();
});
const initForm = () => {
  if (props.instanceId) {
    api.getInstance(props.instanceId).then(res => {
      instance.value = res.data;
    });
  } else {
    instance.value = {
      name: null,
      webServiceUrl: null,
      authenticationEnabled: false,
      authenticationToken: null,
      tlsEnabled: false,
      tlsType: 'PEM',
      tlsCert: null,
      tlsKey: null,
      tlsTrustCert: null,
      tlsKeyStoreType: 'JKS',
      tlsKeyStore: null,
      tlsKeyStorePassword: null,
      tlsTrustStoreType: 'JKS',
      tlsTrustStore: null,
      tlsTrustStorePassword: null
    };
  }
};

const router = useRouter();

const schema = v.object({
  name: v.string([v.trim(), v.minLength(1, 'view.instance.error.name')]),
  webServiceUrl: v.string([v.trim(), v.minLength(1, 'view.instance.error.webServiceUrl')])
});

const { errors, submit } = useValidate();
const save = () => {
  submit(schema, instance.value, () => {
    if (props.instanceId) {
      api.modifyInstance(props.instanceId, instance.value).then(() => {
        toastUtil.success();
        emitter.emit('instance-reload');
        router.back();
      });
    } else {
      api.createInstance(instance.value).then(() => {
        toastUtil.success();
        emitter.emit('instance-reload');
        router.back();
      });
    }
  });
};

const deleteInstance = () => {
  api.deleteInstance(props.instanceId).then(() => {
    toastUtil.success();
    emitter.emit('instance-reload');
    router.back();
  });
};

</script>

<template>
  <DetailPage title="view.instance.detail.title">
    <form @submit.prevent="save">
      <div class="flex flex-col gap-6">
        <FormItem title="view.instance.name" :error="errors['name']">
          <InputText id="name" v-model="instance.name" class="w-80"></InputText>
        </FormItem>
        <FormItem title="view.instance.webServiceUrl" :error="errors['webServiceUrl']">
          <InputText id="webServiceUrl" v-model="instance.webServiceUrl" class="w-80" />
        </FormItem>
        <FormItem title="view.instance.authenticationEnabled">
          <ToggleSwitch id="authenticationEnabled" v-model="instance.authenticationEnabled"
                        required />
        </FormItem>
        <FormItem v-if="instance.authenticationEnabled" title="view.instance.authenticationToken">
          <InputText id="authenticationToken" v-model="instance.authenticationToken"
                     class="w-80" />
        </FormItem>
        <FormItem title="view.instance.tlsEnabled">
          <ToggleSwitch id="tlsEnabled" v-model="instance.tlsEnabled" />
        </FormItem>
        <FormItem title="view.instance.tlsType">
          <SelectButton v-model="instance.tlsType" :options="['PEM','KeyStore']"></SelectButton>
        </FormItem>
        <template v-if="instance.tlsEnabled">
          <template v-if="instance.tlsType==='PEM'">
            <FormItem title="view.instance.tlsCert">
              <Textarea id="tlsCert" rows="5" cols="80" v-model="instance.tlsCert" />
            </FormItem>
            <FormItem title="view.instance.tlsKey">
              <Textarea id="tlsKey" rows="5" cols="80" v-model="instance.tlsKey" />
            </FormItem>
            <FormItem title="view.instance.tlsTrustCert">
              <Textarea id="tlsTrustCert" rows="5" cols="80" v-model="instance.tlsTrustCert" />
            </FormItem>
          </template>
          <template v-else>
            <FormItem title="view.instance.tlsKeyStoreType">
              <SelectButton v-model="instance.tlsKeyStoreType"
                            :options="keyStoreType"></SelectButton>
            </FormItem>
            <FormItem title="view.instance.tlsKeyStorePassword">
              <Password id="tlsKeyStorePassword" inputClass="w-80"
                        v-model="instance.tlsKeyStorePassword" />
            </FormItem>
            <FormItem title="view.instance.tlsKeyStore">
              <Textarea id="tlsKeyStore" rows="5" cols="30" v-model="instance.tlsKeyStore" />
            </FormItem>

            <FormItem title="view.instance.tlsTrustStoreType">
              <SelectButton v-model="instance.tlsTrustStoreType"
                            :options="keyStoreType"></SelectButton>
            </FormItem>
            <FormItem title="view.instance.tlsTrustStorePassword">
              <Password id="tlsTrustStorePassword" inputClass="!w-80"
                        v-model="instance.tlsTrustStorePassword" />
            </FormItem>
            <FormItem title="view.instance.tlsTrustStore">
              <Textarea id="tlsTrustStore" rows="5" cols="80" v-model="instance.tlsTrustStore" />
            </FormItem>

          </template>
        </template>
        <div class="flex gap-4">
          <Button type="submit" icon="pi pi-save" :label="$t('action.save')"></Button>
          <ConfirmDeleteButton :label="$t('action.delete')"
                               @confirm="deleteInstance"></ConfirmDeleteButton>
        </div>
      </div>
    </form>
  </DetailPage>
</template>
