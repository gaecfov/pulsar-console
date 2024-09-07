<script setup>
import {useDialog} from 'primevue/usedialog';
import BrokerRuntimeConfiguration from '@/views/brokers/BrokerRuntimeConfiguration.vue';
import {useI18n} from 'vue-i18n';
import {getVersion, listOwnedNamespaces, shutdownBroker} from '@/service/BrokerService';
import ConfirmButton from '@/components/ConfirmButton.vue';
import toastUtil from '@/util/toast-util';

const props = defineProps(['cluster', 'broker'])
const version = ref('');
const ownerNamespace = ref();
const {t} = useI18n();

onActivated(() => {
  getVersion(props.broker).then(res => {
    version.value = res.data;
  });

  listOwnedNamespaces(props.cluster, props.broker).then(res => {
    ownerNamespace.value = res.data;
  });
});

const dialog = useDialog();
const showRuntimeConfiguration = () => {
  dialog.open(BrokerRuntimeConfiguration, {
    props: {
      header: t('view.broker.runtime-configuration'),
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: {broker: props.broker}
  });
};
const shutdown = () => {
  shutdownBroker(props.broker).then(() => {
    toastUtil.success();
  });
};
</script>

<template>
  <Toolbar>
    <template #start>
      <div class="flex justify-between items-center gap-2">
        {{ broker }}
        <div>
          <Tag v-if="version">{{ version }}</Tag>
        </div>
      </div>
    </template>
    <template #end>
      <div class="flex gap-4">
        <ConfirmButton v-permission="'admin'" icon="pi pi-power-off" :label="$t('action.shutdown')"
                       @confirm="shutdown"></ConfirmButton>
        <Button icon="pi pi-cog" :label="$t('view.broker.runtime-configuration')"
                @click="showRuntimeConfiguration"></Button>
      </div>
    </template>
  </Toolbar>
  <Construction></Construction>
</template>
