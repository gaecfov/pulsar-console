<script setup>
import { useLayout } from '@/layout/composables/layout';
import AppConfigurator from './AppConfigurator.vue';
import { useGlobalStore } from '@/stroes/useGlobalStore';
import { useDialog } from 'primevue/usedialog';
import UserProfile from '@/views/user/UserProfile.vue';
import { usePrimeVue } from 'primevue/config';
import { primeVueLocalesMessages, setLocale } from '@/i18n.config';

const { onMenuToggle, toggleDarkMode, isDarkTheme } = useLayout();
const { t, locale } = useI18n();
const userMenu = ref();
const languageMenu = ref();
const store = useGlobalStore();
const router = useRouter();
const dialog = useDialog();

const primevue = usePrimeVue();
const changeLanguage = (lang) => {
  setLocale(lang);
  primevue.config.locale = primeVueLocalesMessages[lang];
  locale.value = lang;
};
const languageMenuItems = ref([{
  label: '简体中文',
  command: () => {
    changeLanguage('zh-CN');
  }
}, {
  label: 'English',
  command: () => {
    changeLanguage('en');
  }
}]);
const userMenuItems = ref([
  {
    label: t('view.topbar.profile'),
    command: () => {
      dialog.open(UserProfile, {
        props: {
          header: t('view.topbar.profile'),
          modal: true,
          style: {
            width: '400px'
          }
        }
      });
    }
  },
  {
    label: t('view.topbar.sign-out'),
    command: () => {
      store.token = null;
      router.push({ name: 'login' });
    }
  }
]);
const toggle = (event) => {
  userMenu.value.toggle(event);
};
const toggleLanguage = (event) => {
  languageMenu.value.toggle(event);
};

if (isDarkTheme.value) {
  document.documentElement.classList.toggle('app-dark');
}
</script>

<template>
  <div class="layout-topbar">
    <div class="layout-topbar-logo-container">
      <button class="layout-menu-button layout-topbar-action" @click="onMenuToggle">
        <i class="pi pi-bars"></i>
      </button>
      <router-link to="/" class="layout-topbar-logo">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24"
             class="fill-primary">
          <path
            d="M24 8.925h-5.866c-1.586-3.041-3.262-5.402-5.544-5.402c-2.97 0-4.367 2.593-5.717 5.115l-.118.22H0v1.5h3.934c1.39 0 1.673.468 1.673.468c-1.09 1.691-2.4 3.363-4.584 3.363H0v1.574h1.03c4.234 0 6.083-3.434 7.567-6.193c1.361-2.541 2.31-4.08 3.993-4.08c1.747 0 3.584 3.801 5.201 7.157c.237.488.477.988.72 1.483c-6.2.197-9.155 1.649-11.559 2.833c-1.759.866-3.147 1.94-5.433 1.94H0v1.574h1.507c2.754 0 4.47-.85 6.295-1.751c2.53-1.243 5.398-2.652 12.157-2.652h3.907V14.5H21.66a1.18 1.18 0 0 1-.972-.393a71 71 0 0 1-1.133-2.321l-.511-1.047s.366-.393 1.38-.393H24Z"
          />
        </svg>
        <span>Pulsar Console</span>
      </router-link>
    </div>

    <div class="layout-topbar-actions">
      <InstanceSelect v-model="store.instanceId"></InstanceSelect>
      <div class="layout-config-menu">
        <button type="button" class="layout-topbar-action" @click="toggleDarkMode">
          <i :class="['pi', { 'pi-moon': isDarkTheme, 'pi-sun': !isDarkTheme }]"></i>
        </button>
        <div class="relative">
          <button
            v-styleclass="{ selector: '@next', enterFromClass: 'hidden', enterActiveClass: 'animate-scalein', leaveToClass: 'hidden', leaveActiveClass: 'animate-fadeout', hideOnOutsideClick: true }"
            type="button"
            class="layout-topbar-action layout-topbar-action-highlight"
          >
            <i class="pi pi-palette"></i>
          </button>
          <AppConfigurator />
        </div>
      </div>
      <button
        class="layout-topbar-menu-button layout-topbar-action"
        v-styleclass="{ selector: '@next', enterFromClass: 'hidden', enterActiveClass: 'animate-scalein', leaveToClass: 'hidden', leaveActiveClass: 'animate-fadeout', hideOnOutsideClick: true }"
      >
        <i class="pi pi-ellipsis-v"></i>
      </button>
      <button type="button" class="layout-topbar-action" @click="toggleLanguage"
              aria-haspopup="true" aria-controls="overlay_language">
        <i class="pi pi-language"></i>
      </button>
      <a href="mailto:gaecfov@yeah.net" class="layout-topbar-action">
        <i class="pi pi-question-circle"></i>
      </a>
      <Menu ref="languageMenu" id="overlay_language" :model="languageMenuItems" :popup="true" />
      <div class="layout-topbar-menu hidden lg:block">
        <div class="layout-topbar-menu-content">
          <button type="button" class="layout-topbar-action" @click="toggle" aria-haspopup="true"
                  aria-controls="overlay_menu">
            <i class="pi pi-user"></i>
          </button>
          <Menu ref="userMenu" id="overlay_menu" :model="userMenuItems" :popup="true" />
        </div>
      </div>
    </div>
  </div>
</template>
