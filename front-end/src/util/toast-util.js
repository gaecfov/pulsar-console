import { i18n } from '@/i18n.config';

const { global } = i18n;
export default {
  success(message = global.t('success')) {
    this.add({
      severity: 'success',
      summary: global.t('success'),
      detail: message,
      life: 2000
    });
  },
  add(message) {
    if (this.$toast) {
      this.$toast.add(message);
    }
  }
};
