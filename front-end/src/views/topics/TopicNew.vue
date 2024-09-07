<script setup>
import NamespaceSelect from '@/views/components/NamespaceSelect.vue';
import { useRouter } from 'vue-router';
import { createNonPartitionedTopic, createPartitionedTopic } from '@/service/TopicService';
import DetailPage from '@/components/DetailPage.vue';
import TenantSelect from '@/views/components/TenantSelect.vue';
import * as v from 'valibot';
import { useValidate } from '@/hooks/useValidate';

const props = defineProps({
  tenant: {
    type: String,
    required: false
  }, namespace: {
    type: String,
    required: false
  },
  freeMode: {
    type: Boolean,
    default: false
  }
});

const model = reactive({
  name: null,
  tenant: props.tenant,
  namespace: props.namespace,
  persistence: 'persistent',
  partitioned: false,
  partitionNum: 1
});

onActivated(() => {
  model.tenant = props.tenant;
  model.namespace = props.namespace;
  model.name = null;
});

const router = useRouter();
const persistenceOptions = ['persistent', 'non-persistent'];

const schema = v.object({
  name: v.string([v.trim(), v.minLength(1, 'view.topic.error.name')]),
  tenant: v.string([v.trim(), v.minLength(1, 'view.topic.error.tenant')]),
  namespace: v.string([v.trim(), v.minLength(1, 'view.topic.error.namespace')])
});

const { errors, submit } = useValidate();
const save = () => {
  submit(schema, model, () => {
    if (model.partitioned) {
      createPartitionedTopic(model.persistence, model.tenant, model.namespace,
        model.name,
        model.partitionNum).then(() => {
        router.back();
      });
    } else {
      createNonPartitionedTopic(model.persistence, model.tenant,
        model.namespace, model.name).then(
        () => {
          router.back();
        });
    }
  });
};
</script>

<template>
  <DetailPage>
    <template #title>
      {{ $t('view.topic.form.title') }}
    </template>
    <form @submit.prevent="save">
      <div class="flex flex-col gap-4">
        <FormItem title="view.topic.tenant" :error="errors['tenant']">
          <TenantSelect id="tenant" :disabled="!freeMode" v-model="model.tenant" class="w-80"
          ></TenantSelect>
        </FormItem>
        <FormItem title="view.topic.namespace" :error="errors['namespace']">
          <NamespaceSelect id="namespace" :disabled="!freeMode" :tenant="model.tenant" class="w-80"
                           v-model="model.namespace"></NamespaceSelect>
        </FormItem>

        <FormItem title="view.topic.name" :error="errors['name']">
          <InputText id="name" v-model="model.name" class="w-80"></InputText>
        </FormItem>

        <FormItem title="view.topic.persistence">
          <SelectButton v-model="model.persistence" :options="persistenceOptions" />
        </FormItem>
        <FormItem title="view.topic.partitioned">
          <ToggleSwitch v-model="model.partitioned" />
        </FormItem>

        <FormItem v-if="model.partitioned" title="view.topic.partition-num">
          <InputNumber id="partition-num" :min="1" v-model="model.partitionNum" showButtons />
        </FormItem>
        <div class="flex gap-4">
          <Button icon="pi pi-save" type="submit" :label="$t('action.save')"></Button>
        </div>
      </div>
    </form>
  </DetailPage>

</template>
