import { createI18n } from 'vue-i18n';
import primeZhCN from 'primelocale/zh-CN.json';
import primeEn from 'primelocale/en.json';
import zhCN from './locales/zh-CN';
import en from './locales/en';

const language = navigator.language || 'zh-CN';
export const locale = useStorage('language', language);
export const primeVueLocalesMessages = {
  'zh-CN': primeZhCN['zh-CN'],
  zh: primeZhCN['zh-CN'],
  en: primeEn['en']
};
export const getLocale = (lang) => {
  return primeVueLocalesMessages[lang] ?? zhCN['zh-CN'];
};

export const setLocale = (lang) => {
  locale.value = lang;
};

export const i18n = createI18n({
  legacy: false,
  locale: locale.value,
  fallbackLocale: 'zh-CN',
  messages: {
    primevue: primeVueLocalesMessages,
    'zh-CN': zhCN,
    en: en
  }
});
