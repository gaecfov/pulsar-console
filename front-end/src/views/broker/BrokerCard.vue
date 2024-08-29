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
      contentClass: 'h-full w-full',
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: { broker: props.broker }
  });
};

const health = ref();
const ready = ref();

onMounted(() => {
  checkHealth(props.broker.brokerId).then((res) => {
    health.value = res.data;
  });
  checkReady(props.broker.brokerId).then((res) => {
    ready.value = res.data;
  });
});

const configurationMenus = ref([
  {
    label: 'view.broker.card.runtime-configuration',
    command: () => {
      showRuntimeConfiguration();
    }
  }
]);
</script>

<template>
  <div
    class="p-4 border border-surface-200 dark:border-surface-700 bg-surface-0 dark:bg-surface-900 rounded flex flex-col">
    <div>
      <div class="flex flex-row justify-between items-center gap-2">
        <div>
          <div class="text-2xl font-medium mt-1">{{ broker.brokerId }}</div>
        </div>
        <div class="flex gap-2">
          <Tag v-if="health==='ok'" :value="$t('running')" severity="success"></Tag>
          <Tag v-else :value="$t('view.broker.card.health-unknown')" severity="warn"></Tag>

          <Tag v-if="ready==='ok'" :value="$t('ready')" severity="success"></Tag>
          <Tag v-else :value="$t('view.broker.card.ready-unknown')" severity="warn"></Tag>
        </div>
      </div>
      <div class="flex justify-between gap-6 mt-6">
        <div>
          <Button v-permission="'admin'" size="small" severity="warn" icon="pi pi-power-off"
                  v-tooltip="$t('view.broker.list.shutdown')"></Button>
        </div>
        <div class="flex gap-2">
          <Button icon="pi pi-desktop" severity="secondary" :label="$t('stats')"
                  @click="showBrokerStats" />
          <SplitButton icon="pi pi-cog" severity="secondary" :label="$t('configuration')"
                       :model="configurationMenus">
            <template #item="{label}">
              <div class="p-tieredmenu-item-content">
                <a class="p-tieredmenu-item-link">
                  <span class="p-tieredmenu-item-label">{{ $t(label) }}</span>
                </a>
              </div>
            </template>
          </SplitButton>
        </div>
      </div>
    </div>
  </div>
</template>
