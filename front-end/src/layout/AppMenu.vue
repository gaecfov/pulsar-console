<script setup>
import AppMenuItem from '@/layout/AppMenuItem.vue';

const model = ref([
  {
    label: 'menu.management',
    items: [
      { label: 'menu.instance', icon: 'pi pi-cloud', to: '/instances' },
      { label: 'menu.cluster', icon: 'pi pi-server', to: '/clusters' },
      { label: 'menu.tenant', icon: 'pi pi-sitemap', to: '/tenants' },
      { label: 'menu.namespace', icon: 'pi pi-home', to: '/namespaces' },
      { label: 'menu.topic', icon: 'pi pi-inbox', to: '/topics' },
      { label: 'menu.client-certificate', icon: 'pi pi-id-card', to: '/client-certificates' },
      { label: 'menu.client-token', icon: 'pi pi-ticket', to: '/client-tokens' },
      { label: 'menu.subscription-relationship', icon: 'pi pi-bell', to: '/subscription-relationship' },
    ]
  },

  {
    label: 'menu.system',
    items: [{ label: 'menu.user', icon: 'pi pi-user', to: '/users' }]
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
