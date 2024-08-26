<script setup>
import { useDialog } from 'primevue/usedialog';
import BrokerRuntimeConfiguration from '@/views/broker/BrokerRuntimeConfiguration.vue';
import { checkHealth, checkReady } from '@/service/BrokerService';
import BrokerStats from '@/views/broker/BrokerStats.vue';

const props = defineProps(['broker']);

const { t } = useI18n();
const dialog = useDialog();
const showRuntimeConfiguration = () => {
  dialog.open(BrokerRuntimeConfiguration, {
    props: {
      header: t('view.broker.card.runtime-configuration'),
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: { broker: props.broker }
  });
};
const showBrokerStats = () => {
  dialog.open(BrokerStats, {
    props: {
      header: t('view.broker.card.stats'),
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: { broker: props.broker }
  });
};

const health = ref(true);
const ready = ref(true);

onMounted(() => {
  checkHealth().then((res) => {
    health.value = 'ok' === res.data;
  });
  checkReady().then((res) => {
    ready.value = 'ok' === res.data;
  });
});

const configurationMenus = [
  {
    label: t('view.broker.card.runtime-configuration'),
    command: () => {
      showRuntimeConfiguration();
    }
  }
];
</script>

<template>
  <div class="p-4 border border-surface-200 dark:border-surface-700 bg-surface-0 dark:bg-surface-900 rounded flex flex-col">
    <div>
      <div class="flex flex-row justify-between items-center gap-2">
        <div>
          <div class="text-2xl font-medium mt-1">{{ broker.brokerId }}</div>
        </div>
        <div class="flex gap-2">
          <Badge v-if="health" :value="$t('running')" severity="success"></Badge>
          <Badge v-else :value="$t('stopped')" severity="danger"></Badge>
          <Badge v-if="ready" :value="$t('ready')" severity="success"></Badge>
        </div>
      </div>
      <div class="flex justify-between gap-6 mt-6">
        <div>
          <Button v-permission="'admin'" size="small" severity="warn" icon="pi pi-power-off" v-tooltip="$t('view.broker.list.shutdown')"></Button>
        </div>
        <div class="flex gap-2">
          <Button icon="pi pi-desktop" severity="secondary" :label="$t('stats')" @click="showBrokerStats" />
          <SplitButton icon="pi pi-cog" severity="secondary" :label="$t('configuration')" :model="configurationMenus" />
        </div>
      </div>
    </div>
  </div>
</template>
