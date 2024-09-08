<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import { getPolicy, postPolicy, removePolicy } from '@/service/TopicService';
import toastUtil from '@/util/toast-util';

const topic = inject('topic');

const messageTTL = ref();
const retention = ref();
const compactionThreshold = ref();

const messageTTLValue = ref();
const retentionValue = ref();
const compactionThresholdValue = ref();

onMounted(() => {
  load();
});

watch(() => topic, () => {
  load();
});

const loadPolicy = (policyName, displayTarget, valueTarget, defaultValue) => {
  getPolicy(topic.value, policyName).then(res => {
    displayTarget.value = res.data;
    valueTarget.value = res.data || defaultValue;
  });
};
const load = () => {
  loadPolicy('messageTTL', messageTTL, messageTTLValue);
  loadPolicy('retention', retention, retentionValue, {});
  loadPolicy('compactionThreshold', compactionThreshold, compactionThresholdValue);
};
const saveMessageTTL = (close) => {
  postPolicy(topic.value, 'messageTTL', null, {
    messageTTL: messageTTLValue.value
  }).then(() => {
    toastUtil.success();
    loadPolicy('messageTTL', messageTTL, messageTTLValue);
    close();
  });
};

const removeMessageTTL = (close) => {
  removePolicy(topic.value, 'messageTTL').then(() => {
    toastUtil.success();
    loadPolicy('messageTTL', messageTTL, messageTTLValue);
    close();
  });
};

const saveRetention = (close) => {
  postPolicy(topic.value, 'retention', retentionValue.value).then(() => {
    toastUtil.success();
    loadPolicy('retention', retention, retentionValue, {});
    close();
  });
};

const removeRetention = (close) => {
  removePolicy(topic.value, 'retention').then(() => {
    toastUtil.success();
    loadPolicy('retention', retention, retentionValue, {});
    close();
  });
};

const saveCompactionThreshold = (close) => {
  postPolicy(topic.value, 'compactionThreshold', compactionThresholdValue.value).then(() => {
    toastUtil.success();
    loadPolicy('compactionThreshold', compactionThreshold, compactionThresholdValue);
    close();
  });
};

const removeCompactionThreshold = (close) => {
  removePolicy(topic.value, 'compactionThreshold').then(() => {
    toastUtil.success();
    loadPolicy('compactionThreshold', compactionThreshold, compactionThresholdValue);
    close();
  });
};

</script>
<template>
  <PolicyArea title="view.components.cleanup">
    <div class="grid grid-cols-4 gap-4">
      <PolicyItem title="view.components.messageTTL" clearable
                  :display="messageTTL" unit="sec"
                  @save="saveMessageTTL"
                  @remove="removeMessageTTL">
        <InputNumber v-model="messageTTLValue" show-buttons></InputNumber>
      </PolicyItem>
      <PolicyItem title="view.components.compactionThreshold" clearable
                  :display="compactionThreshold"
                  @save="saveCompactionThreshold"
                  @remove="removeCompactionThreshold">
        <InputNumber v-model="compactionThresholdValue" show-buttons></InputNumber>
      </PolicyItem>
      <PolicyItem class="col-span-2" title="view.components.retention" clearable
                  :display="retention"
                  @save="saveRetention"
                  @remove="removeRetention">
        <template #display="{data}">
          <div class="w-full grid grid-cols-2 gap-4">
            <PolicyValue title="view.components.retentionSizeInMB" unit="megabyte"
                         :value="data.retentionSizeInMB"></PolicyValue>
            <PolicyValue title="view.components.retentionTimeInMinutes" unit="minutes"
                         :value="data.retentionTimeInMinutes"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-2 gap-4">
          <FormItem title="view.components.retentionSizeInMB">
            <InputNumber v-model="retentionValue.retentionSizeInMB" show-buttons
                         :suffix="$t('megabyte')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.retentionTimeInMinutes">
            <InputNumber v-model="retentionValue.retentionTimeInMinutes" :suffix="$t('minutes')"
                         show-buttons></InputNumber>
          </FormItem>
        </div>
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
