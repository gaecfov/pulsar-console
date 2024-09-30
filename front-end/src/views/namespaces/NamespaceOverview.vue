<script setup>
import ConfirmButton from '@/components/ConfirmButton.vue';
import * as ns from '@/service/NamespaceService';
import toastUtil from '@/util/toast-util';
import FormItem from '@/components/FormItem.vue';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import { useEmitter } from '@/hooks/useEmitter';

const props = defineProps(['tenant', 'namespace']);
const boundaries = ref([]);
const emitter = useEmitter();
onMounted(() => {
  loadBundles();
});
onActivated(() => {
  loadBundles();
});

const loadBundles = () => {
  ns.getPolicy(props.tenant, props.namespace, 'bundles').then(res => {
    const data = res.data;
    const list = [];
    for (let n = 0; n < data.numBundles; n++) {
      list.push({ 'bundle': data.boundaries[n] + '_' + data.boundaries[n + 1] });
    }
    boundaries.value = list;
  });
};

const unloadAll = () => {
  ns.unload(props.tenant, props.namespace).then(() => {
    toastUtil.success();
  });
};
const cleanAllBacklog = () => {
  ns.clearBacklog(props.tenant, props.namespace).then(() => {
    toastUtil.success();
  });
};
const unloadBundle = (bundle) => {
  ns.unloadBundle(props.tenant, props.namespace, bundle).then(() => {
    toastUtil.success();
  });
};

const clearBundleBacklog = (bundle) => {
  ns.clearBundleBacklog(props.tenant, props.namespace, bundle).then(() => {
    toastUtil.success();
  });
};

const bundleOp = ref();
const currentBundle = ref();
const bundleSplitAlgorithms = ['range_equally_divide', 'topic_count_equally_divide',
  'specified_positions_divide', 'flow_or_qps_equally_divide'];
const bundleSplitOptions = reactive({
  authoritative: false,
  unload: false,
  splitAlgorithmName: null
});
const showBundleSplitDialog = (event, bundle) => {
  currentBundle.value = bundle;
  bundleOp.value.toggle(event);
};

const splitBundle = () => {
  ns.splitBundle(props.tenant, props.namespace, currentBundle.value, null, bundleSplitOptions).then(
    () => {
      toastUtil.success();
      loadBundles();
      bundleOp.value.toggle();
    });
};
const deleteBundle = (bundle) => {
  ns.deleteBundle(props.tenant, props.namespace, bundle).then(() => {
    toastUtil.success();
    loadBundles();
  });
};
const router = useRouter();
const deleteNamespace = () => {
  ns.deleteNamespace(props.tenant, props.namespace).then(() => {
    toastUtil.success();
    emitter.emit('namespace-reload');
    router.push({
      name: 'namespaces'
    });
  });
};
</script>
<template>
  <div class="flex flex-col gap-4">
    <Panel header="Bundles">
      <Toolbar>
        <template #start>
          <div class="flex gap-4">
            <ConfirmButton icon="pi pi-sync" :label="$t('view.namespace.action.unload-all')"
                           @confirm="unloadAll"></ConfirmButton>
            <ConfirmButton icon="pi pi-times"
                           :label="$t('view.namespace.action.clear-all-backlog')"
                           @confirm="cleanAllBacklog"></ConfirmButton>
          </div>
        </template>
      </Toolbar>
      <Popover ref="bundleOp">
        <div class="flex flex-col gap-4 p-4">
          <FormItem title="view.namespace.bundle.split.authoritative">
            <ToggleSwitch v-model="bundleSplitOptions.authoritative"></ToggleSwitch>
          </FormItem>
          <FormItem title="view.namespace.bundle.split.unload">
            <ToggleSwitch v-model="bundleSplitOptions.unload"></ToggleSwitch>
          </FormItem>
          <FormItem title="view.namespace.bundle.split.splitAlgorithmName">
            <Select :options="bundleSplitAlgorithms" class="w-80"
                    v-model="bundleSplitOptions.splitAlgorithmName"></Select>
          </FormItem>
          <div>
            <Button icon="pi pi-share-alt" :label="$t('view.namespace.action.split')"
                    @click="splitBundle"></Button>
          </div>
        </div>
      </Popover>
      <DataTable class="mt-4" :value="boundaries" show-gridlines>
        <Column field="bundle" header="Bundle" headerClass="w-80" bodyClass="items-center">
          <template #body="{data}">
            <span class="text-lg font-bold">{{ data.bundle }}</span>
          </template>
        </Column>
        <Column>
          <template #body="{data}">
            <div class="flex-grow flex gap-2">
              <Button icon="pi pi-share-alt" size="small"
                      @click="showBundleSplitDialog($event,data.bundle)"
                      :label="$t('view.namespace.action.split')"></Button>
              <ConfirmButton icon="pi pi-sync" size="small" @confirm="unloadBundle(data.bundle)"
                             :label="$t('view.namespace.action.unload')"></ConfirmButton>
              <ConfirmButton icon="pi pi-times" size="small"
                             @confirm="clearBundleBacklog(data.bundle)"
                             :label="$t('view.namespace.action.clear-backlog')"></ConfirmButton>
              <ConfirmDeleteButton size="small" :label="$t('action.delete')"
                                   @confirm="deleteBundle(data.bundle)"></ConfirmDeleteButton>
            </div>
          </template>
        </Column>
      </DataTable>
    </Panel>
    <Panel>
      <template #header>
        <span class="font-bold text-red-500">{{ $t('view.namespace.danger') }}</span>
      </template>
      <Toolbar>
        <template #start>
          <div class="flex gap-4">
            <ConfirmDeleteButton icon="pi pi-trash" :label="$t('action.delete')"
                                 @confirm="deleteNamespace"></ConfirmDeleteButton>
          </div>
        </template>
      </Toolbar>
    </Panel>
  </div>
</template>
