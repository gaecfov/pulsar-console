<script setup>

import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { getPolicy, postPolicy, removePolicy } from '@/service/TopicService';
import toastUtil from '@/util/toast-util';

const topic = inject('topic');

const subscribeRate = ref();
const subscribeRateValue = ref();
const subscriptionDispatchRate = ref();
const subscriptionDispatchRateValue = ref();

onMounted(() => {
  load();
});

watch(() => topic, () => {
  load();
});

const load = () => {
  loadPolicy('subscribeRate', subscribeRate, subscribeRateValue, {});
  loadPolicy('subscriptionDispatchRate', subscriptionDispatchRate, subscriptionDispatchRateValue,
    {});
};

const loadPolicy = (policyName, displayTarget, valueTarget, defaultValue) => {
  getPolicy(topic.value, policyName).then(res => {
    displayTarget.value = res.data;
    valueTarget.value = res.data || defaultValue;
  });
};

const saveSubscribeRate = (close) => {
  postPolicy(topic.value, 'subscribeRate', subscriptionDispatchRateValue.value).then(() => {
    toastUtil.success();
    loadPolicy('subscribeRate', subscribeRate, subscribeRateValue);
    close();
  });
};

const removeSubscribeRate = (close) => {
  removePolicy(topic.value, 'subscribeRate').then(() => {
    toastUtil.success();
    loadPolicy('subscribeRate', subscribeRate, subscribeRateValue);
    close();
  });
};

const saveSubscriptionDispatchRate = (close) => {
  postPolicy(topic.value, 'subscriptionDispatchRate', subscriptionDispatchRateValue.value).then(
    () => {
      toastUtil.success();
      loadPolicy('subscriptionDispatchRate', subscriptionDispatchRate,
        subscriptionDispatchRateValue, {});
      close();
    });
};

const removeSubscriptionDispatchRate = (close) => {
  removePolicy(topic.value, 'subscriptionDispatchRate').then(() => {
    toastUtil.success();
    loadPolicy('subscriptionDispatchRate', subscriptionDispatchRate, subscriptionDispatchRateValue,
      {});
    close();
  });
};


</script>
<template>
  <PolicyArea title="view.components.dispatch-rate">
    <PolicyItem title="view.components.subscription-dispatch-rate" :inline="false"
                :display="subscriptionDispatchRate" clearable
                @save="saveSubscriptionDispatchRate"
                @remove="removeSubscriptionDispatchRate">
      <template #display="{data}">
        <div class="w-full grid grid-cols-4 gap-4">
          <PolicyValue title="view.components.dispatchThrottlingRateInByte" unit="bytes"
                       :value="data.dispatchThrottlingRateInByte"></PolicyValue>
          <PolicyValue title="view.components.dispatchThrottlingRateInMsg"
                       :value="data.dispatchThrottlingRateInMsg"></PolicyValue>
          <PolicyValue title="view.components.ratePeriodInSecond" unit="sec"
                       :value="data.ratePeriodInSecond"></PolicyValue>
          <PolicyValue title="view.components.relativeToPublishRate"
                       :value="data.relativeToPublishRate"></PolicyValue>
        </div>
      </template>
      <div class="grid grid-cols-4 gap-4">
        <FormItem title="view.components.dispatchThrottlingRateInByte">
          <InputNumber
            v-model="subscriptionDispatchRateValue.dispatchThrottlingRateInByte"
            :suffix="$t('bytes')" show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.dispatchThrottlingRateInMsg">
          <InputNumber
            v-model="subscriptionDispatchRateValue.dispatchThrottlingRateInMsg"
            show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.ratePeriodInSecond">
          <InputNumber v-model="subscriptionDispatchRateValue.ratePeriodInSecond"
                       show-buttons
                       :suffix="$t('sec')"></InputNumber>
        </FormItem>
        <FormItem title="view.components.relativeToPublishRate">
          <ToggleSwitch
            v-model="subscriptionDispatchRateValue.relativeToPublishRate"></ToggleSwitch>
        </FormItem>
      </div>
    </PolicyItem>

    <PolicyItem title="view.components.subscribe-rate" :inline="false"
                :display="subscribeRate" clearable
                @save="saveSubscribeRate"
                @remove="removeSubscribeRate">
      <template #display="{data}">
        <div class="grid grid-cols-4 gap-4">
          <PolicyValue title="view.components.ratePeriodInSecond" unit="sec"
                       :value="data.ratePeriodInSecond"></PolicyValue>
          <PolicyValue title="view.components.subscribeThrottlingRatePerConsumer"
                       :value="data.subscribeThrottlingRatePerConsumer"></PolicyValue>
        </div>
      </template>
      <div class="grid grid-cols-4 gap-4">
        <FormItem title="view.components.ratePeriodInSecond">
          <InputNumber v-model="subscribeRateValue.ratePeriodInSecond"
                       :suffix="$t('sec')"></InputNumber>
        </FormItem>
        <FormItem title="view.components.subscribeThrottlingRatePerConsumer">
          <InputNumber
            v-model="subscribeRateValue.subscribeThrottlingRatePerConsumer"></InputNumber>
        </FormItem>
      </div>
    </PolicyItem>
  </PolicyArea>
</template>
