import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import Aura from '@primevue/themes/aura';
import PrimeVue from 'primevue/config';
import ConfirmationService from 'primevue/confirmationservice';
import ToastService from 'primevue/toastservice';
import DialogService from 'primevue/dialogservice';

import adminApi from '@/util/pulsar-api';
import consoleApi from '@/util/console-api';
import toastHelper from '@/util/toast-util';

import '@/assets/styles.scss';
import '@/assets/tailwind.css';
import { getLocale, i18n, locale } from '@/i18n.config';

import permission from '@/directives/permission';

const app = createApp(App);
const pinia = createPinia();
app
  .use(pinia)
  .use(router)
  .use(i18n)
  .use(PrimeVue, {
    theme: {
      preset: Aura,
      options: {
        darkModeSelector: '.app-dark'
      }
    },
    locale: getLocale(locale.value)
  })
  .use(ToastService)
  .use(DialogService)
  .use(ConfirmationService)
  .directive('permission', permission)
  .mount('#app');

adminApi.$toast = app.config.globalProperties.$toast;
consoleApi.$toast = app.config.globalProperties.$toast;
toastHelper.$toast = app.config.globalProperties.$toast;
