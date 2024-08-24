<script setup>
import TopicConsumer from '@/views/topic/TopicConsumer.vue';
import MetricCard from '@/components/MetricCard.vue';
import {useConfirm} from 'primevue/useconfirm';
import {skipAllMessage, skipMessage} from '@/service/TopicService';
import toastUtil from '@/util/toast-util';
import {formatRate} from '@/util/formatter';
import {useI18n} from "vue-i18n";

const {t} = useI18n()
const stats = inject('topic-stats');
const topic = inject('topic');
const subscriptions = computed(() => {
  if (stats.value.subscriptions) {
    return Object.keys(stats.value.subscriptions).map((x) => {
      return {subscription: x, ...stats.value.subscriptions[x]};
    });
  }
  return [];
});

const confirm = useConfirm();
const confirmSkipAllMessage = (event, sub) => {
  confirm.require({
    target: event.currentTarget,
    message: t('confirm.message.skip-all-message'),
    accept: () => {
      skipAllMessage(topic, sub.subscription).then(() => {
        toastUtil.success();
      });
    }
  });
};

const skipNum = ref(0);
const confirmSkipMessage = (event, sub) => {
  confirm.require({
    target: event.currentTarget,
    group: 'skipMessages',
    accept: () => {
      skipMessage(topic, sub.subscription, skipNum.value).then(() => {
        toastUtil.success();
      });
    }
  });
};

const isOnline = (sub) => {
  return sub.consumers && sub.consumers.length > 0;
};
</script>

<template>
  <ConfirmPopup group="skipMessages">
    <template #message>
      <div class="flex-auto p-4">
        <label for="skipNum" class="font-bold block mb-2"> {{
            $t('confirm.message.skip-message-num')
          }} </label>
        <InputNumber v-model="skipNum" inputId="skipNum" fluid/>
      </div>
    </template>
  </ConfirmPopup>
  <DataView :value="subscriptions">
    <template #list="{ items }">
      <div class="flex flex-col gap-4">
        <Panel v-for="item in items" :key="item.subscription" toggleable collapsed>
          <template #header>
            <div class="flex gap-x-2">
              <span class="font-bold">{{ item.subscription }}</span>
              <Tag>{{ item.type }}</Tag>
              <Tag v-if="item.durable" severity="secondary">Durable</Tag>
              <Tag v-if="isOnline(item)" severity="success">Online</Tag>
              <Tag v-else severity="warn">Offline</Tag>
            </div>
          </template>
          <div class="flex flex-col p-4 gap-4 bg-pc-main">
            <div class="grid grid-cols-4 gap-2">
              <MetricCard title="msgBacklog" :value="item.msgBacklog"
                          icon="pi pi-inbox dark:bg-blue-400 bg-blue-200">
                <template #action>
                  <Button text :label="$t('view.topic-stats.subscription.skip')" size="small"
                          @click="confirmSkipMessage($event, item)"></Button>
                  <Button text :label="$t('view.topic-stats.subscription.skip-all')" size="small"
                          @click="confirmSkipAllMessage($event, item)"></Button>
                </template>
              </MetricCard>
              <MetricCard title="messageAckRate" :value="formatRate(item.messageAckRate)"
                          icon="pi pi-check dark:bg-green-400 bg-green-200">
              </MetricCard>

              <MetricCard title="unackedMessages" :value="item.unackedMessages"
                          icon="pi pi-hourglass dark:bg-orange-400 bg-orange-200">
              </MetricCard>
              <MetricCard title="msgOutCounter" :value="item.msgOutCounter"
                          icon="pi pi-upload dark:bg-pink-400 bg-pink-200">
              </MetricCard>
            </div>
            <TopicConsumer v-for="consumer in item.consumers" :key="consumer.consumerName"
                           :consumer="consumer"></TopicConsumer>
          </div>
        </Panel>
      </div>
    </template>
  </DataView>
</template>
