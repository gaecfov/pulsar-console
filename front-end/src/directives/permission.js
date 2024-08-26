import { useGlobalStore } from '@/stroes/useGlobalStore';

export default {
  mounted(el, binding) {
    const store = useGlobalStore();
    const userRole = store.currentUser.role;
    const requiredPermissions = binding.value;
    if (!requiredPermissions) {
      return;
    }
    console.log(userRole,requiredPermissions)
    const hasPermission = userRole.toLowerCase() === requiredPermissions.toLowerCase();
    if (!hasPermission) {
      el.parentNode && el.parentNode.removeChild(el);
    }
  }
};
