<script setup>
import { ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';

const model = ref([
  {
    label: 'menu.pulsar',
    items: [
      { label: 'menu.instance', icon: 'pi pi-cloud', to: '/instance' },
      { label: 'menu.cluster', icon: 'pi pi-server', to: '/cluster' },
      { label: 'menu.broker', icon: 'pi pi-desktop', to: '/broker' },
      { label: 'menu.tenant', icon: 'pi pi-sitemap', to: '/tenant' },
      { label: 'menu.namespace', icon: 'pi pi-home', to: '/namespace' },
      { label: 'menu.topic', icon: 'pi pi-inbox', to: '/topic' }
    ]
  },

  {
    label: 'menu.system',
    items: [{ label: 'menu.user', icon: 'pi pi-user', to: '/user' }]
  }
]);
</script>

<template>
  <ul class="layout-menu">
    <template v-for="(item, i) in model" :key="item">
      <app-menu-item v-if="!item.separator" :item="item" :index="i"></app-menu-item>
      <li v-if="item.separator" class="menu-separator"></li>
    </template>
  </ul>
</template>
