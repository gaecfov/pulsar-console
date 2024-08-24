<script setup>
import { getNamespaceConfig, unloadNamespace } from '@/service/NamespaceService';
import { useConfirm } from 'primevue/useconfirm';
import { deconstructionNamespace } from '@/util/namespace-util';
import toastUtil from '@/util/toast-util';
import { useDialog } from 'primevue/usedialog';
import JsonViewer from '@/components/JsonViewer.vue';
import NamespacePolicy from '@/views/namespace/NamespacePolicy.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const dialog = useDialog();
const confirm = useConfirm();
const dialogRef = inject('dialogRef');
const namespace = deconstructionNamespace(dialogRef.value.data.namespace);

const stats = ref({});
provide('namespace-stats', stats);
onMounted(() => {
  getNamespaceConfig(namespace.tenant, namespace.namespace).then((res) => {
    stats.value = res.data;
  });
});
const confirmUnload = (event) => {
  confirm.require({
    target: event.currentTarget,
    message: t('confirm.message.unload'),
    icon: 'pi pi-exclamation-triangle',
    rejectProps: {
      label: t('cancel'),
      severity: 'secondary',
      outlined: true
    },
    acceptProps: {
      label: t('unload'),
      severity: 'warn'
    },
    accept: () => {
      unloadNamespace(namespace.tenant, namespace).then(() => {
        toastUtil.success();
      });
    }
  });
};
const showJson = () => {
  dialog.open(JsonViewer, {
    props: {
      header: 'JSON',
      modal: true,
      style: {
        width: '60dvw',
        height: '80dvh'
      }
    },
    data: stats.value
  });
};
</script>

<template>
  <div class="h-full bg-pc-main p-4 overflow-auto">
    <Tabs value="base">
      <div class="flex justify-between">
        <TabList>
          <Tab value="base">{{ $t('view.namespace.stats.base') }}</Tab>
          <Tab value="policy">{{ $t('view.namespace.stats.policy') }}</Tab>
        </TabList>
        <div class="flex items-center gap-2">
          <Button icon="pi pi-code" @click="showJson"></Button>
        </div>
      </div>
      <TabPanels>
        <TabPanel value="base">
          <Panel>
            <div class="grid grid-cols-4 gap-4">
              <MetricCard class="bg-pc-main" title="encryption_required" :value="stats.encryption_required">
              </MetricCard>
              <MetricCard class="bg-pc-main" title="subscription_auth_mode"
                          :value="stats.subscription_auth_mode"></MetricCard>
              <MetricCard class="bg-pc-main" title="offload_threshold"
                          :value="stats.offload_threshold"></MetricCard>
              <MetricCard class="bg-pc-main" title="offload_threshold_in_seconds"
                          :value="stats.offload_threshold_in_seconds"></MetricCard>
              <MetricCard class="bg-pc-main" title="schema_compatibility_strategy"
                          :value="stats.schema_compatibility_strategy"></MetricCard>
              <MetricCard class="bg-pc-main" title="is_allow_auto_update_schema"
                          :value="stats.is_allow_auto_update_schema">
              </MetricCard>
              <MetricCard class="bg-pc-main" title="schema_validation_enforced"
                          :value="stats.schema_validation_enforced">
              </MetricCard>
              <MetricCard class="bg-pc-main" title="subscription_types_enabled"
                          :value="stats.subscription_types_enabled">
                <template #value="{data}">
                  {{ data}}
                </template>
              </MetricCard>
            </div>
          </Panel>
        </TabPanel>
        <TabPanel value="policy">
          <Toolbar class="mb-6">
            <template #start>
              <Button :label="$t('unload')" icon="pi pi-refresh" severity="warn"
                      @click="confirmUnload" />
            </template>
          </Toolbar>
          <NamespacePolicy></NamespacePolicy>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>
</template>
