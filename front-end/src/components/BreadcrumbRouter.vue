<script setup>
import { useRoute } from 'vue-router';

const route = useRoute();
const home = ref({
  icon: 'pi pi-home'
});
const breadcrumbs = computed(() => {
  return route.matched.map(r => {
    return {
      label: r.meta?.breadcrumb || r.name,
      route: r.path,
      hide: r.meta.hideInBreadcrumb
    };
  }).filter(b => !b.hide);
});
</script>
<template>
  <Breadcrumb :model="breadcrumbs" :home="home">
    <template #item="{ item,props }">
      <router-link v-if="item.route" :to="item.route">
        <span :class="[item.icon]" />
        <span class=" font-semibold" v-if="item.label">{{ $t(item.label) }}</span>
      </router-link>
      <a v-else>
        <span :class="[item.icon]" />
        <span class="text-surface-700 dark:text-surface-0" v-if="item.label">{{ $t(item.label)
          }}</span>
      </a>
    </template>
  </Breadcrumb>
</template>
