<script setup>
import { useLayout } from '@/layout/composables/layout';
import AppConfigurator from './AppConfigurator.vue';
import { useGlobalStore } from '@/stroes/useGlobalStore';
import { useDialog } from 'primevue/usedialog';
import UserProfile from '@/views/users/UserProfile.vue';
import { usePrimeVue } from 'primevue/config';
import { primeVueLocalesMessages, setLocale } from '@/i18n.config';
import InstanceSelect from '@/views/components/InstanceSelect.vue';

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
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 512 512"
             class="fill-primary">
          <path
            d="M260.402 78.5956C275.044 78.0422 275.894 91.6264 277.65 101.972L297.83 213.324C302.942 240.726 304.158 235.741 330.175 235.747L414.805 235.741C420.86 235.74 427.452 234.943 432.938 238.052C440.693 242.447 442.878 253.432 438.445 260.9C432.429 271.036 420.91 269.277 410.837 269.257L328.556 269.275C310.5 269.316 302.958 264.289 298.736 283.944C297.435 290.003 296.286 296.071 295.098 302.152L278.619 387.617C277.178 394.837 274.386 415.662 271.386 420.692C267.123 427.842 256.429 429.626 249.649 425.228C243.442 421.201 242.641 412.392 241.183 405.801L223.048 323.833C220.554 311.949 218.113 300.065 215.334 288.243C211.671 272.654 213.749 269.248 195.671 269.245L97.1787 269.273C90.7763 269.289 83.5688 270.152 77.9597 266.387C70.3676 261.292 68.1025 250.642 73.33 243.057C79.4614 234.161 89.557 235.745 98.9276 235.744L140.418 235.743C163.76 235.724 161.826 239.205 170.736 216.186L190.437 165.981C191.615 162.9 192.686 159.252 194.605 156.541C198.67 150.798 207.399 150.833 212.637 154.78C217.827 158.69 219.402 172.105 220.942 178.047L241.058 257.01C243.264 265.664 246.047 274.167 247.414 283.008C248.41 275.884 247.41 261.296 247.41 253.408L247.412 109.523C247.398 98.0081 244.481 80.7083 260.402 78.5956Z" />
        </svg>
        <span>Pulsar Console</span>
      </router-link>
    </div>
    <div class="layout-topbar-actions">
      <InstanceSelect class="w-52" v-model="store.instanceId"></InstanceSelect>
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
