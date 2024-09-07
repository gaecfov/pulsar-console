<script setup>
import AppConfigurator from '@/layout/AppConfigurator.vue';
import { useLayout } from '@/layout/composables/layout';

const {toggleDarkMode, isDarkTheme} = useLayout();
const icon = computed(() => {
  return isDarkTheme.value ? "pi pi-moon" : "pi pi-sun"
})
</script>

<template>
  <div class="fixed flex gap-4 top-8 right-8">
    <Button type="button" @click="toggleDarkMode" rounded
            :icon="icon" severity="secondary"/>
    <div class="relative">
      <Button icon="pi pi-palette"
              v-styleclass="{ selector: '@next', enterFromClass: 'hidden', enterActiveClass: 'animate-scalein', leaveToClass: 'hidden', leaveActiveClass: 'animate-fadeout', hideOnOutsideClick: true }"
              type="button" rounded/>
      <AppConfigurator/>
    </div>
  </div>
</template>
