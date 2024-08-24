<script setup>
import {
  getNamespacePolicies,
  removeNamespaceMessageTTL,
  setNamespaceMessageTTL,
  unloadNamespace
} from '@/service/NamespaceService';
import { useConfirm } from 'primevue/useconfirm';
import { deconstructionNamespace } from '@/util/namespace-util';
import toastUtil from '@/util/toast-util';
import { useDialog } from 'primevue/usedialog';
import JsonViewer from '@/components/JsonViewer.vue';
import NamespaceAdvancePolicy from '@/views/namespace/NamespaceAdvancePolicy.vue';
import { useI18n } from 'vue-i18n';
import MetricCard from '@/components/MetricCard.vue';
import Metric from '@/components/Metric.vue';

const { t } = useI18n();
const dialog = useDialog();
const confirm = useConfirm();
const dialogRef = inject('dialogRef');
const namespace = deconstructionNamespace(dialogRef.value.data.namespace);

const policies = ref({});
provide('namespace-policies', policies);
provide('namespace', namespace);
onMounted(() => {
  getNamespacePolicies(namespace.tenant, namespace.namespace).then((res) => {
    policies.value = res.data;
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
    data: policies.value
  });
};
const setTTL = (ttlSeconds) => {
  setNamespaceMessageTTL(namespace.tenant, namespace.namespace, ttlSeconds).then(() => {
    policies.value.message_ttl_in_seconds = ttlSeconds;
    toastUtil.success();
  });
};
const clearTTL = () => {
  removeNamespaceMessageTTL(namespace.tenant, namespace.namespace).then(() => {
    policies.value.message_ttl_in_seconds = undefined;
    toastUtil.success();
  });
};
</script>

<template>
  <div class="h-full bg-pc-main p-4 overflow-auto">
    <Tabs value="base">
      <div class="flex justify-between">
        <TabList>
          <Tab value="base">{{ $t('view.namespace.policies.common') }}</Tab>
          <Tab value="policy">{{ $t('view.namespace.policies.advance') }}</Tab>
        </TabList>
        <div class="flex items-center gap-2">
          <Button icon="pi pi-code" @click="showJson"></Button>
        </div>
      </div>
      <TabPanels>
        <TabPanel value="base">
          <Panel>
            <div class="grid grid-cols-4 gap-4">
              <MetricCard title="message_ttl_in_seconds" icon="pi-clock" class="bg-pc-main" editable
                          show-clear @clear="clearTTL"
                          :value="policies.message_ttl_in_seconds" @submit="setTTL">
                <template #value="{data}">
                  <Metric v-if="data" icon="pi-clock" :value="data" unit="sec"></Metric>
                </template>
              </MetricCard>

              <MetricCard class="bg-pc-main" title="encryption_required"
                          :value="policies.encryption_required">
              </MetricCard>
              <MetricCard class="bg-pc-main" title="subscription_auth_mode"
                          :value="policies.subscription_auth_mode"></MetricCard>
              <MetricCard class="bg-pc-main" title="offload_threshold"
                          :value="policies.offload_threshold"></MetricCard>
              <MetricCard class="bg-pc-main" title="offload_threshold_in_seconds"
                          :value="policies.offload_threshold_in_seconds"></MetricCard>
              <MetricCard class="bg-pc-main" title="schema_compatibility_strategy"
                          :value="policies.schema_compatibility_strategy"></MetricCard>
              <MetricCard class="bg-pc-main" title="is_allow_auto_update_schema"
                          :value="policies.is_allow_auto_update_schema">
              </MetricCard>
              <MetricCard class="bg-pc-main" title="schema_validation_enforced"
                          :value="policies.schema_validation_enforced">
              </MetricCard>
              <MetricCard class="bg-pc-main" title="subscription_types_enabled"
                          :value="policies.subscription_types_enabled">
                <template #value="{data}">
                  {{ data }}
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
          <NamespaceAdvancePolicy></NamespaceAdvancePolicy>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>
</template>
