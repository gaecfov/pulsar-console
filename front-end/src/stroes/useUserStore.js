import * as us from '@/service/UserService';
import { listUsers } from '@/service/UserService';
import toast from '@/util/toast-util';

export const useUserStore = defineStore('user-store', () => {
  const users = ref([]);
  const loading = ref(false);

  const reload = () => {
    loading.value = true;
    listUsers().then((res) => {
      users.value = res.data;
      loading.value = false;
    });
  };

  const createUser = (user) => {
    return us.createUser(user).then(() => {
      toast.success();
      reload();
    });
  };
  const deleteUser = (userId) => {
    return us.deleteUser(userId).then(() => {
      toast.success();
      reload();
    });
  };
  const changePassword = (userId, password) => {
    return us.changePassword(userId, password).then(() => {
      toast.success();
      reload();
    });
  };
  return { users, loading, reload, createUser, deleteUser, changePassword };
});
