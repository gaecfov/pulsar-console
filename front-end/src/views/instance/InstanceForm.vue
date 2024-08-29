<script setup>
import { useInstanceStore } from '@/views/instance/useInstanceStore';
import { isEmpty } from '@/util/assert';

const store = useInstanceStore();
const instance = reactive({
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
});

const dialogRef = inject('dialogRef');
Object.assign(instance, dialogRef.value.data);
const save = () => {
  if (instance.id) {
    store.modifyInstance(instance.id, instance).then(() => {
      dialogRef.value.close();
    });
  } else {
    store.createInstance(instance).then(() => {
      dialogRef.value.close();
    });
  }
};
const keyStoreType = ['JKS', 'PKCS12', 'JCEKS', 'PKCS11', 'BKS'];
</script>

<template>
  <form @submit.prevent="save">
    <Tabs value="base">
      <TabList>
        <Tab value="base">{{ $t('view.instance.form.base') }}</Tab>
        <Tab value="tls">{{ $t('view.instance.form.tls') }}</Tab>
      </TabList>
      <TabPanels>
        <TabPanel value="base">
          <div class="flex flex-col gap-6">
            <div>
              <label for="name" class="block font-bold mb-3">{{ $t('name') }}</label>
              <InputText id="name" v-model="instance.name" fluid required></InputText>
            </div>
            <div>
              <label for="webServiceUrl" class="block font-bold mb-3">{{ $t('view.instance.webServiceUrl')
                }}</label>
              <InputText id="webServiceUrl" v-model="instance.webServiceUrl" class="w-full"
                         required />
              <small v-if="!instance.webServiceUrl" class="text-red-500">{{$t('required')}}</small>
            </div>
            <div>
              <label for="authenticationEnabled" class="block font-bold mb-3">{{ $t(
                'view.instance.authenticationEnabled') }}</label>
              <ToggleSwitch id="authenticationEnabled" v-model="instance.authenticationEnabled"
                            required />
            </div>
            <div v-if="instance.authenticationEnabled">
              <label for="authenticationToken" class="block font-bold mb-3">{{ $t(
                'view.instance.authenticationToken') }}</label>
              <InputText id="authenticationToken" v-model="instance.authenticationToken"
                         :invalid="instance.authenticationEnabled && isEmpty(instance.authenticationToken)"
                         fluid required />
            </div>
          </div>
        </TabPanel>
        <TabPanel value="tls">
          <div class="flex flex-col gap-6">
            <div>
              <label for="tlsEnabled" class="block font-bold mb-3">{{ $t(
                'view.instance.tlsEnabled') }}</label>
              <ToggleSwitch id="tlsEnabled" v-model="instance.tlsEnabled"
                            required />
            </div>
            <div>
              <label for="tlsType" class="block font-bold mb-3">{{ $t(
                'view.instance.tlsType') }}</label>
              <SelectButton v-model="instance.tlsType" :options="['PEM','KeyStore']"></SelectButton>
            </div>
            <template v-if="instance.tlsEnabled">

              <template v-if="instance.tlsType==='PEM'">
                <div>
                  <label for="tlsCert" class="block font-bold mb-3">{{ $t(
                    'view.instance.tlsCert') }}</label>
                  <Textarea id="tlsCert" fluid v-model="instance.tlsCert" />
                </div>
                <div>
                  <label for="tlsKey" class="block font-bold mb-3">{{ $t(
                    'view.instance.tlsKey') }}</label>
                  <Textarea id="tlsKey" fluid v-model="instance.tlsKey" />
                </div>
                <div>
                  <label for="tlsTrustCert" class="block font-bold mb-3">{{ $t(
                    'view.instance.tlsTrustCert') }}</label>
                  <Textarea id="tlsTrustCert" fluid v-model="instance.tlsTrustCert" />
                </div>
              </template>
              <template v-else>
                <div class="grid grid-cols-2 gap-2">
                  <div>
                    <div>
                      <label for="tlsKeyStoreType" class="block font-bold mb-3">{{ $t(
                        'view.instance.tlsKeyStoreType') }}</label>
                      <SelectButton v-model="instance.tlsKeyStoreType"
                                    :options="keyStoreType"></SelectButton>
                    </div>
                    <div>
                      <label for="tlsKeyStoreType" class="block font-bold mb-3">{{ $t(
                        'view.instance.tlsKeyStore') }}</label>
                      <Textarea id="tlsKeyStore" fluid v-model="instance.tlsKeyStore" />
                    </div>
                    <div>
                      <label for="tlsKeyStorePassword" class="block font-bold mb-3">{{ $t(
                        'view.instance.tlsKeyStorePassword') }}</label>
                      <InputText id="tlsKeyStorePassword" fluid
                                 v-model="instance.tlsKeyStorePassword" />
                    </div>
                  </div>
                  <div>
                    <div>
                      <label for="tlsTrustStoreType" class="block font-bold mb-3">{{ $t(
                        'view.instance.tlsTrustStoreType') }}</label>
                      <SelectButton v-model="instance.tlsTrustStoreType"
                                    :options="keyStoreType"></SelectButton>
                    </div>
                    <div>
                      <label for="tlsTrustStore" class="block font-bold mb-3">{{ $t(
                        'view.instance.tlsTrustStore') }}</label>
                      <Textarea id="tlsTrustStore" fluid v-model="instance.tlsTrustStore" />
                    </div>
                    <div>
                      <label for="tlsTrustStorePassword" class="block font-bold mb-3">{{ $t(
                        'view.instance.tlsTrustStorePassword') }}</label>
                      <InputText id="tlsTrustStorePassword" fluid
                                 v-model="instance.tlsTrustStorePassword" />
                    </div>
                  </div>
                </div>
              </template>
            </template>
          </div>
        </TabPanel>
      </TabPanels>
    </Tabs>
    <div class="p-4">
      <Button type="submit" icon="pi pi-save" :label="$t('save')"></Button>
    </div>
  </form>
</template>
