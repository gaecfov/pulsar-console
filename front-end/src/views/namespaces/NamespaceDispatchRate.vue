<script setup>

import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const dispatchRate = useNamespacePolicy('dispatchRate', p => {
  if (p.topicDispatchRate) {
    const keys = Object.keys(p.topicDispatchRate);
    return p.topicDispatchRate[keys[0]];
  }
  return undefined;
}, v => v ?? {});
const subscriptionDispatchRate = useNamespacePolicy('subscriptionDispatchRate', p => {
  if (p.subscriptionDispatchRate) {
    const keys = Object.keys(p.subscriptionDispatchRate);
    return p.subscriptionDispatchRate[keys[0]];
  }
  return undefined;
}, v => v ?? {});
const subscribeRate = useNamespacePolicy('subscribeRate', p => {

  if (p.clusterSubscribeRate) {
    const keys = Object.keys(p.clusterSubscribeRate);
    return p.clusterSubscribeRate[keys[0]];
  }
  return undefined;
}, v => v ?? {});

</script>
<template>
  <PolicyArea title="view.components.dispatch-rate">
    <PolicyItem title="view.components.topic-dispatch-rate" :inline="false"
                :display="dispatchRate.policyDisplay.value" clearable
                @save="dispatchRate.postPolicy"
                @remove="dispatchRate.removePolicy">
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
          <InputNumber v-model="dispatchRate.policyValue.value.dispatchThrottlingRateInByte"
                       :suffix="$t('bytes')" show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.dispatchThrottlingRateInMsg">
          <InputNumber v-model="dispatchRate.policyValue.value.dispatchThrottlingRateInMsg"
                       show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.ratePeriodInSecond">
          <InputNumber v-model="dispatchRate.policyValue.value.ratePeriodInSecond" show-buttons
                       :suffix="$t('sec')"></InputNumber>
        </FormItem>
        <FormItem title="view.components.relativeToPublishRate">
          <ToggleSwitch v-model="dispatchRate.policyValue.value.relativeToPublishRate"></ToggleSwitch>
        </FormItem>
      </div>
    </PolicyItem>

    <PolicyItem title="view.components.subscription-dispatch-rate" :inline="false"
                :display="subscriptionDispatchRate.policyDisplay.value" clearable
                @save="subscriptionDispatchRate.postPolicy"
                @remove="subscriptionDispatchRate.removePolicy">
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
            v-model="subscriptionDispatchRate.policyValue.value.dispatchThrottlingRateInByte"
            :suffix="$t('bytes')" show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.dispatchThrottlingRateInMsg">
          <InputNumber
            v-model="subscriptionDispatchRate.policyValue.value.dispatchThrottlingRateInMsg"
            show-buttons></InputNumber>
        </FormItem>
        <FormItem title="view.components.ratePeriodInSecond">
          <InputNumber v-model="subscriptionDispatchRate.policyValue.value.ratePeriodInSecond"
                       show-buttons
                       :suffix="$t('sec')"></InputNumber>
        </FormItem>
        <FormItem title="view.components.relativeToPublishRate">
          <ToggleSwitch
            v-model="subscriptionDispatchRate.policyValue.value.relativeToPublishRate"></ToggleSwitch>
        </FormItem>
      </div>
    </PolicyItem>

    <PolicyItem title="view.components.subscribe-rate" :inline="false"
                :display="subscribeRate.policyDisplay.value" clearable
                @save="subscribeRate.postPolicy"
                @remove="subscribeRate.removePolicy">
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
          <InputNumber v-model="subscribeRate.policyValue.value.ratePeriodInSecond"
                       :suffix="$t('sec')"></InputNumber>
        </FormItem>
        <FormItem title="view.components.subscribeThrottlingRatePerConsumer">
          <InputNumber
            v-model="subscribeRate.policyValue.value.subscribeThrottlingRatePerConsumer"></InputNumber>
        </FormItem>
      </div>
    </PolicyItem>
  </PolicyArea>
</template>
