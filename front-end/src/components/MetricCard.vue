<script setup>
import { useI18n } from 'vue-i18n';
import { formatDate, formatRate, formatStorageSize, formatThroughput } from '@/util/formatter';

const props = defineProps({
  label: String,
  title: String,
  icon: String,
  value: {},
  valueType: String
});

const { t } = useI18n();

</script>
<template>
  <Card v-bind="$attrs">
    <template #title>
      <div class="flex justify-between gap-4 text-sm">
        <slot name="title">
            <span class="block text-surface-500 dark:text-surface-400 font-medium mb-4">
            <template v-if="title">
              {{ $t(title) }}
            </template>
            <template v-else>
              {{ label }}
            </template>
            </span>
        </slot>
        <slot name="icon">
          <i v-if="icon"
             class="flex-shrink-0 dark:bg-blue-400 bg-blue-200 h-8 w-8 items-center flex justify-center rounded pi"
             :class="icon"></i>
        </slot>
      </div>
    </template>
    <template #content>
      <div class="flex flex-grow text-2xl text-primary">
        <slot name="value" :value="value">
          <template v-if="valueType==='rate'">
            {{ formatRate(value) }}
          </template>
          <template v-else-if="valueType==='date'">
            {{ formatDate(value) }}
          </template>
          <template v-else-if="valueType==='throughput'">
            {{ formatThroughput(value) }}
          </template>
          <template v-else-if="valueType==='throughput'">
            {{ formatStorageSize(value) }}
          </template>
          <template v-else-if="valueType==='storage'">
            {{ formatStorageSize(value) }}
          </template>
          <template v-else>
            {{ value }}
          </template>
        </slot>
      </div>
    </template>
    <template #footer>
      <slot name="action">
      </slot>
    </template>
  </Card>
</template>
