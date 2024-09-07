<script setup>
import PolicyArea from '@/views/components/PolicyArea.vue';
import PolicyItem from '@/views/components/PolicyItem.vue';
import PolicyValue from '@/views/components/PolicyValue.vue';
import FormItem from '@/components/FormItem.vue';
import {getPolicy, postPolicy, removePolicy} from "@/service/TopicService";
import toastUtil from "@/util/toast-util";


const topic = inject("topic")
const destinationStorage = ref();
const destinationStorageValue = ref({});
const messageAge = ref();
const messageAgeValue = ref({});

onMounted(() => {
  loadBacklogQuota();
})
const loadBacklogQuota = () => {
  getPolicy(topic.value, 'backlogQuotaMap').then(res => {
    if (res.data) {
      destinationStorage.value = res.data.destination_storage || {};
      destinationStorageValue.value = res.data.destination_storage || {};
      messageAge.value = res.data.message_age || {};
      messageAgeValue.value = res.data.message_age || {};
    } else {
      destinationStorage.value = {};
      destinationStorageValue.value = {};
      messageAge.value = {};
      messageAgeValue.value = {};
    }
  })
}

const policies = ['producer_request_hold', 'consumer_backlog_eviction', 'producer_exception'];
const emit = defineEmits(['save', 'remove']);
const save = (close, policyName, policy, params) => {
  postPolicy(topic.value, policyName, policy, params).then(() => {
    toastUtil.success()
    loadBacklogQuota();
    close();
  })
};
const remove = (close, policyName, params) => {
  removePolicy(topic.value, policyName, params).then(() => {
    toastUtil.success()
    loadBacklogQuota();
    close();
  })
};
</script>
<template>
  <PolicyArea title="view.components.backlog" toggleable>
    <div class="grid grid-cols-2 gap-4">
      <PolicyItem title="view.components.destinationStorage" clearable
                  :display="destinationStorage"
                  @save="save($event,'backlogQuota',destinationStorageValue,{
                    backlogQuotaType:'destination_storage'
                  })"
                  @remove="remove($event,'backlogQuota',{
                    backlogQuotaType:'destination_storage'
                  })">
        <template #display="{data}">
          <div class="w-full grid grid-cols-2 gap-4">
            <PolicyValue title="view.components.limitSize" unit="megabyte"
                         :value="data.limitSize"></PolicyValue>
            <PolicyValue title="view.components.policy"
                         :value="data.policy"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-2 gap-4">
          <FormItem title="view.components.limitSize">
            <InputNumber v-model="destinationStorageValue.limitSize" show-buttons
                         :suffix="$t('megabyte')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.policy">
            <Select v-model="destinationStorageValue.policy" class="w-80"
                    :options="policies"></Select>
          </FormItem>
        </div>
      </PolicyItem>
      <PolicyItem title="view.components.messageAge" clearable
                  :display="messageAge"
                  @save="save($event,'backlogQuota',messageAgeValue,{
                    backlogQuotaType:'message_age'
                  })"
                  @remove="remove($event,'backlogQuota',{
                    backlogQuotaType:'message_age'
                  })">
        <template #display="{data}">
          <div class="w-full grid grid-cols-2 gap-4">
            <PolicyValue title="view.components.limitTime" unit="sec"
                         :value="data.limitTime"></PolicyValue>
            <PolicyValue title="view.components.policy"
                         :value="data.policy"></PolicyValue>
          </div>
        </template>
        <div class="grid grid-cols-2 gap-4">
          <FormItem title="view.components.limitTime">
            <InputNumber v-model="messageAgeValue.limitTime" show-buttons
                         :suffix="$t('sec')">
            </InputNumber>
          </FormItem>
          <FormItem title="view.components.policy">
            <Select v-model="messageAgeValue.policy" class="w-80"
                    :options="policies"></Select>
          </FormItem>
        </div>
      </PolicyItem>
    </div>
  </PolicyArea>
</template>
