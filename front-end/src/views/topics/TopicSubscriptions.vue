<script setup>
import TopicConsumer from '@/views/topics/TopicConsumer.vue';
import MetricCard from '@/components/MetricCard.vue';
import { useConfirm } from 'primevue/useconfirm';
import * as ts from '@/service/TopicService';
import toastUtil from '@/util/toast-util';
import { formatRate } from '@/util/formatter';
import ConfirmButton from '@/components/ConfirmButton.vue';
import TopicPeekMessages from '@/views/topics/TopicPeekMessages.vue';

const stats = inject('topic-stats');
const topic = inject('topic');
const subscriptions = computed(() => {
  if (stats.value && stats.value.subscriptions) {
    return Object.keys(stats.value.subscriptions).map((x) => {
      return { subscription: x, ...stats.value.subscriptions[x] };
    });
  }
  return [];
});

const confirm = useConfirm();
const confirmSkipAllMessage = (sub) => {
  ts.skipAllMessages(topic.value, sub.subscription).then(() => {
    toastUtil.success();
  });
};

const skipNum = ref(0);
const confirmSkipMessage = (event, sub) => {
  confirm.require({
    target: event.currentTarget,
    group: 'skipMessages',
    accept: () => {
      ts.skipMessages(topic.value, sub.subscription, skipNum.value).then(() => {
        toastUtil.success();
      });
    }
  });
};

const isOnline = (sub) => {
  return sub.consumers && sub.consumers.length > 0;
};

const peekVisible = ref(false);
const currentSubscription = ref();
const showPeekMessages = (sub) => {
  currentSubscription.value = sub.subscription;
  peekVisible.value = true;
};

const expirySubscription = (sub) => {
  ts.expiryMessages(topic.value, sub.subscription).then(() => {
    toastUtil.success();
  });
};
</script>

<template>
  <Drawer v-model:visible="peekVisible" position="right" class="!w-1/2"
          :header="$t('view.topic.action.peek')">
    <TopicPeekMessages :topic="topic" :subscription="currentSubscription"></TopicPeekMessages>
  </Drawer>
  <ConfirmPopup group="skipMessages">
    <template #message>
      <div class="flex-auto p-4">
        <label for="skipNum" class="font-bold block mb-2"> {{
            $t('view.topic.subscription.skip-num"')
          }} </label>
        <InputNumber v-model="skipNum" inputId="skipNum" fluid />
      </div>
    </template>
  </ConfirmPopup>
  <div class="flex flex-col gap-4">
    <Toolbar>
      <template #start>
        <div class="flex gap-2">
          <ConfirmButton size="small" icon="pi pi-clock" severity="danger"
                         @confirm="ts.expiryAllMessages(topic,0).then(()=>toastUtil.success())"
                         :label="$t('view.topic.action.expiry-all')"></ConfirmButton>
        </div>
      </template>
    </Toolbar>
    <DataView :value="subscriptions">
      <template #list="{ items }">
        <div class="flex flex-col gap-4">
          <Panel v-for="item in items" :key="item.subscription" toggleable>
            <template #header>
              <div class="flex gap-x-2">
                <span class="font-bold text-xl">{{ item.subscription }}</span>
                <Tag>{{ item.type }}</Tag>
                <Tag v-if="item.durable" severity="secondary">Durable</Tag>
                <Tag v-if="isOnline(item)" severity="success">Online</Tag>
                <Tag v-else severity="warn">Offline</Tag>
              </div>
            </template>
            <div class="flex flex-col gap-4">
              <Toolbar>
                <template #start>
                  <div class="flex gap-2">
                    <Button icon="pi pi-eye" :label="$t('view.topic.action.peek')" size="small"
                            @click="showPeekMessages(item)"></Button>
                    <Button icon="pi pi-forward" :label="$t('view.topic.action.skip')" size="small"
                            @click="confirmSkipMessage($event, item)"></Button>
                    <ConfirmButton icon="pi pi-fast-forward"
                                   :label="$t('view.topic.action.skip-all')" size="small"
                                   @confirm="confirmSkipAllMessage(item)"></ConfirmButton>
                    <ConfirmButton size="small" icon="pi pi-clock"
                                   @confirm="expirySubscription(item)"
                                   :label="$t('view.topic.action.expiry-all')"></ConfirmButton>

                  </div>
                </template>
              </Toolbar>
              <div class="grid grid-cols-5 gap-4">
                <MetricCard title="view.topic.subscription.msgBacklog" :value="item.msgBacklog"
                            icon="pi pi-inbox dark:bg-blue-500 bg-blue-200">

                </MetricCard>
                <MetricCard title="view.topic.subscription.messageAckRate" :value="formatRate(item.messageAckRate)"
                            icon="pi pi-check dark:bg-green-500 bg-green-200">
                </MetricCard>

                <MetricCard title="view.topic.subscription.unackedMessages" :value="item.unackedMessages"
                            icon="pi pi-hourglass dark:bg-orange-500 bg-orange-200">
                </MetricCard>
                <MetricCard title="view.topic.subscription.msgOutCounter" :value="item.msgOutCounter"
                            icon="pi pi-upload dark:bg-pink-500 bg-pink-200">
                </MetricCard>
                <MetricCard title="view.topic.subscription.msgDelayed" :value="item.msgDelayed"
                            icon="pi pi-clock dark:bg-violet-500 bg-violet-200">
                </MetricCard>
              </div>
              <Panel v-if="isOnline(item)" :header="$t('view.topic.consumers')" toggleable
                     collapsed>
                <div class="flex flex-col gap-4">
                  <TopicConsumer v-for="consumer in item.consumers" :key="consumer.consumerName"
                                 :consumer="consumer"></TopicConsumer>
                </div>
              </Panel>
            </div>
          </Panel>
        </div>
      </template>
    </DataView>
  </div>
</template>
