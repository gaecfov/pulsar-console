<script setup>
import * as ts from '@/service/TopicService';
import MetricCard from '@/components/MetricCard.vue';
import { isSystemTopic } from '@/util/topic-util';
import { useConfirm } from 'primevue/useconfirm';
import { useI18n } from 'vue-i18n';
import toastUtil from '@/util/toast-util';
import Metric from '@/components/Metric.vue';

const stats = inject('topic-stats');
const topic = toRaw(inject('topic'));

const ttlSeconds = ref();
const retentionConfiguration = ref();
const persistenceConfiguration = ref();

onMounted(() => {
  ts.getTopicTTLSeconds(topic).then((res) => {
    ttlSeconds.value = res.data;
  });

  ts.getTopicRetentionConfiguration(topic).then((res) => {
    retentionConfiguration.value = res.data;
  });
  ts.getTopicPersistenceConfiguration(topic).then((res) => {
    persistenceConfiguration.value = res.data;
  });
});
const { t } = useI18n();
const confirm = useConfirm();
const _ttl = ref(0);
const setTTLConfirm = () => {
  confirm.require({
    header: t('ttlSeconds'),
    group: 'ttl',
    acceptLabel: t('save'),
    rejectLabel: t('cancel'),
    rejectProps: {
      label: t('cancel'),
      outlined: true,
      size: 'small'
    },
    acceptProps: {
      label: t('save'),
      size: 'small'
    },
    accept: () => {
      ts.setTopicTTLSeconds(topic, _ttl.value).then(() => {
        ttlSeconds.value = _ttl.value;
        toastUtil.success();
      });
    }
  });
};
const clearTTL = () => {
  ts.clearTopicTTLSeconds(topic).then(() => {
    ttlSeconds.value = null;
    toastUtil.success();
  });
};

const _retentionConfiguration = reactive({
  retentionSizeInMB: 0,
  retentionTimeInMinutes: 0
});
const setRetentionConfiguration = () => {
  if (retentionConfiguration.value) {
    Object.assign(_retentionConfiguration, retentionConfiguration.value);
  }
  confirm.require({
    header: t('retentionConfiguration'),
    group: 'retentionConfiguration',
    acceptLabel: t('save'),
    rejectLabel: t('cancel'),
    rejectProps: {
      label: t('cancel'),
      outlined: true,
      size: 'small'
    },
    acceptProps: {
      label: t('save'),
      size: 'small'
    },
    accept: () => {
      ts.setTopicRetentionConfiguration(topic, _retentionConfiguration).then(() => {
        retentionConfiguration.value = _retentionConfiguration;
        toastUtil.success();
      });
    }
  });
};

const clearRetentionConfiguration = () => {
  ts.clearTopicRetentionConfiguration(topic).then(() => {
    retentionConfiguration.value = null;
    toastUtil.success();
  });
};

const _persistenceConfiguration = reactive({
  bookkeeperAckQuorum: 2,
  bookkeeperEnsemble: 2,
  bookkeeperWriteQuorum: 2,
  managedLedgerMaxMarkDeleteRate: null
});

const setPersistenceConfiguration = () => {
  if (persistenceConfiguration.value) {
    Object.assign(_persistenceConfiguration, persistenceConfiguration.value);
  }
  confirm.require({
    header: t('persistenceConfiguration'),
    group: 'persistenceConfiguration',

    acceptLabel: t('save'),
    rejectLabel: t('cancel'),
    rejectProps: {
      label: t('cancel'),
      outlined: true,
      size: 'small'
    },
    acceptProps: {
      label: t('save'),
      size: 'small'
    },
    accept: () => {
      ts.setTopicPersistenceConfiguration(topic, _persistenceConfiguration).then(() => {
        persistenceConfiguration.value = _persistenceConfiguration;
        toastUtil.success();
      });
    }
  });
};

const clearPersistenceConfiguration = () => {
  ts.clearTopicPersistenceConfiguration(topic).then(() => {
    retentionConfiguration.value = null;
    toastUtil.success();
  });
};
</script>

<template>
  <ConfirmDialog group="ttl">
    <template #message>
      <div class="flex-auto">
        <label for="ttlSeconds" class="font-bold block mb-2">
          {{ $t('ttlSeconds') }}
        </label>
        <InputNumber
          id="ttlSeconds" fluid showButtons buttonLayout="horizontal"
          v-model="_ttl"
        ></InputNumber>
      </div>
    </template>
  </ConfirmDialog>
  <ConfirmDialog group="retentionConfiguration">
    <template #message>
      <div class="flex flex-col gap-2 w-72">
        <div class="flex-auto">
          <label for="retentionTimeInMinutes" class="font-bold block mb-2">
            {{ $t('retentionSizeInMB') }}
          </label>
          <InputNumber
            id="retentionSizeInMB" fluid showButtons buttonLayout="horizontal"
            v-model="_retentionConfiguration.retentionSizeInMB"
          ></InputNumber>
        </div>

        <div class="flex-auto">
          <label for="retentionTimeInMinutes" class="font-bold block mb-2">
            {{ $t('retentionTimeInMinutes') }}
          </label>
          <InputNumber
            id="retentionTimeInMinutes" fluid showButtons buttonLayout="horizontal"
            v-model="_retentionConfiguration.retentionTimeInMinutes"
          ></InputNumber>
        </div>
      </div>
    </template>
  </ConfirmDialog>
  <ConfirmDialog group="persistenceConfiguration">
    <template #message>
      <div class="flex flex-col gap-2  w-72">
        <div class="flex-auto">
          <label for="bookkeeperEnsemble" class="font-bold block mb-2">
            {{ $t('bookkeeperEnsemble') }}
          </label>
          <InputNumber
            id="bookkeeperEnsemble" fluid showButtons buttonLayout="horizontal"
            v-model="_persistenceConfiguration.bookkeeperEnsemble"
          ></InputNumber>
        </div>

        <div class="flex-auto">
          <label for="bookkeeperWriteQuorum" class="font-bold block mb-2">
            {{ $t('bookkeeperWriteQuorum') }}
          </label>
          <InputNumber
            id="bookkeeperWriteQuorum" fluid showButtons buttonLayout="horizontal"
            v-model="_persistenceConfiguration.bookkeeperWriteQuorum"
          ></InputNumber>
        </div>

        <div class="flex-auto">
          <label for="bookkeeperAckQuorum" class="font-bold block mb-2">
            {{ $t('bookkeeperAckQuorum') }}
          </label>
          <InputNumber
            id="bookkeeperAckQuorum" fluid showButtons buttonLayout="horizontal"
            v-model="_persistenceConfiguration.bookkeeperAckQuorum"
          ></InputNumber>
        </div>

        <div class="flex-auto">
          <label for="managedLedgerMaxMarkDeleteRate" class="font-bold block mb-2">
            {{ $t('managedLedgerMaxMarkDeleteRate') }}
          </label>
          <InputNumber
            id="managedLedgerMaxMarkDeleteRate" fluid showButtons buttonLayout="horizontal"
            v-model="_persistenceConfiguration.managedLedgerMaxMarkDeleteRate"
          ></InputNumber>
        </div>
      </div>
    </template>
  </ConfirmDialog>
  <Panel>
    <div class="flex flex-col gap-y-4">
      <div class="flex gap-2">
        <Button
          severity="warn"
          size="small"
          :label="$t('unload')"
          icon="pi pi-sync"
        ></Button>
      </div>
      <div class="grid grid-cols-4 gap-2">
        <template v-if="!isSystemTopic(topic.fullTopic)">
          <MetricCard
            title="ttlSeconds" :value="ttlSeconds" class="bg-pc-main" editable @edit="setTTLConfirm"
            showClear @clear="clearTTL" icon="pi pi-clock"></MetricCard>
          <MetricCard
            title="retentionConfiguration" :value="retentionConfiguration"
            class="bg-pc-main"
            editable
            @edit="setRetentionConfiguration"
            showClear
            @clear="clearRetentionConfiguration"
            icon="pi pi-database"
          >
            <template #value="{data}">
              <div class="flex flex-col gap-2" v-if="data">
                <Metric title="retentionSizeInMB" :value="data.retentionSizeInMB"></Metric>
                <Metric title="retentionTimeInMinutes"
                        :value="data.retentionTimeInMinutes"></Metric>
              </div>
            </template>
          </MetricCard>
          <MetricCard
            title="persistenceConfiguration"
            :value="stats.persistence"
            class="bg-pc-main"
            editable
            @edit="setPersistenceConfiguration"
            showClear
            @clear="clearPersistenceConfiguration"
            icon="pi pi-save"
          >
            <template #value="{data}">
              <div class="flex justify-between" v-if="data">
                <Chip label="E">
                  <span>E</span>
                  <Badge :value="data.bookkeeperEnsemble"></Badge>
                </Chip>
                <Chip label="Q">
                  <span>W</span>
                  <Badge severity="warn" :value="data.bookkeeperWriteQuorum"></Badge>
                </Chip>
                <Chip label="A">
                  <span>E</span>
                  <Badge severity="success" :value="data.bookkeeperAckQuorum"></Badge>
                </Chip>
              </div>
            </template>
          </MetricCard>
        </template>
        <MetricCard
          title="replication"
          :value="stats.replication"
          class="bg-pc-main"
          icon="pi pi-clone"
        ></MetricCard>
      </div>
    </div>
  </Panel>
</template>
