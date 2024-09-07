<script setup>

import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import { useNamespacePolicy } from '@/hooks/useNamespacePolicy';

const maxProducersPerTopic = useNamespacePolicy('maxProducersPerTopic',
  p => p.max_producers_per_topic);
const maxSubscriptionsPerTopic = useNamespacePolicy('maxSubscriptionsPerTopic',
  p => p.max_subscriptions_per_topic);
const maxConsumersPerTopic = useNamespacePolicy('maxConsumersPerTopic',
  p => p.max_consumers_per_topic);
const maxConsumersPerSubscription = useNamespacePolicy('maxConsumersPerSubscription',
  p => p.max_consumers_per_subscription);
const maxTopicsPerNamespace = useNamespacePolicy('maxTopicsPerNamespace',
  p => p.max_topics_per_namespace);
const maxUnackedMessagesPerConsumer = useNamespacePolicy('maxUnackedMessagesPerConsumer',
  p => p.max_unacked_messages_per_consumer);
const maxUnackedMessagesPerSubscription = useNamespacePolicy('maxUnackedMessagesPerSubscription',
  p => p.max_unacked_messages_per_subscription);
</script>

<template>
  <PolicyArea title="view.component.throttling">
    <div class="grid grid-cols-4 gap-4">
      <PolicyItem title="view.components.maxProducersPerTopic" clearable
                  :display="maxProducersPerTopic.policyDisplay.value"
                  @save="maxProducersPerTopic.postPolicy"
                  @remove="maxProducersPerTopic.removePolicy">
        <InputNumber v-model="maxProducersPerTopic.policyValue.value" show-buttons></InputNumber>
      </PolicyItem>

      <PolicyItem title="view.components.maxSubscriptionsPerTopic" clearable
                  :display="maxSubscriptionsPerTopic.policyDisplay.value"
                  @save="maxSubscriptionsPerTopic.postPolicy"
                  @remove="maxSubscriptionsPerTopic.removePolicy">
        <InputNumber v-model="maxSubscriptionsPerTopic.policyValue.value"
                     show-buttons></InputNumber>
      </PolicyItem>

      <PolicyItem title="view.components.maxConsumersPerTopic" clearable
                  :display="maxConsumersPerTopic.policyDisplay.value"
                  @save="maxConsumersPerTopic.postPolicy"
                  @remove="maxConsumersPerTopic.removePolicy">
        <InputNumber v-model="maxConsumersPerTopic.policyValue.value" show-buttons></InputNumber>
      </PolicyItem>

      <PolicyItem title="view.components.maxConsumersPerSubscription" clearable
                  :display="maxConsumersPerSubscription.policyDisplay.value"
                  @save="maxConsumersPerSubscription.postPolicy"
                  @remove="maxConsumersPerSubscription.removePolicy">
        <InputNumber v-model="maxConsumersPerSubscription.policyValue.value"
                     show-buttons></InputNumber>
      </PolicyItem>

      <PolicyItem title="view.components.maxTopicsPerNamespace" clearable
                  :display="maxTopicsPerNamespace.policyDisplay.value"
                  @save="maxTopicsPerNamespace.postPolicy"
                  @remove="maxTopicsPerNamespace.removePolicy">
        <InputNumber v-model="maxTopicsPerNamespace.policyValue.value" show-buttons></InputNumber>
      </PolicyItem>

      <PolicyItem title="view.components.maxUnackedMessagesPerConsumer" clearable
                  :display="maxUnackedMessagesPerConsumer.policyDisplay.value"
                  @save="maxUnackedMessagesPerConsumer.postPolicy"
                  @remove="maxUnackedMessagesPerConsumer.removePolicy">
        <InputNumber v-model="maxUnackedMessagesPerConsumer.policyValue.value"
                     show-buttons></InputNumber>
      </PolicyItem>

      <PolicyItem title="view.components.maxUnackedMessagesPerSubscription" clearable
                  :display="maxUnackedMessagesPerSubscription.policyDisplay.value"
                  @save="maxUnackedMessagesPerSubscription.postPolicy"
                  @remove="maxUnackedMessagesPerSubscription.removePolicy">
        <InputNumber v-model="maxUnackedMessagesPerSubscription.policyValue.value"
                     show-buttons></InputNumber>
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
