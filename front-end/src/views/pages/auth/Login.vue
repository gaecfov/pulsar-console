<script setup>
import FloatingConfigurator from '@/components/FloatingConfigurator.vue';
import { ref } from 'vue';
import { useGlobalStore } from '@/stroes/useGlobalStore';

const username = ref('');
const password = ref('');
const store = useGlobalStore();

const signIn = () => {
  store.signIn({
    username: username.value,
    password: password.value
  });
};
</script>

<template>
  <FloatingConfigurator />
  <div
    class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] overflow-hidden">
    <div class="flex flex-col items-center justify-center">
      <div
        style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
        <div class="w-full bg-surface-0 dark:bg-surface-900 py-20 px-8 sm:px-20"
             style="border-radius: 53px">
          <div class="flex flex-col items-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24"
                 class="fill-primary">
              <path
                d="M24 8.925h-5.866c-1.586-3.041-3.262-5.402-5.544-5.402c-2.97 0-4.367 2.593-5.717 5.115l-.118.22H0v1.5h3.934c1.39 0 1.673.468 1.673.468c-1.09 1.691-2.4 3.363-4.584 3.363H0v1.574h1.03c4.234 0 6.083-3.434 7.567-6.193c1.361-2.541 2.31-4.08 3.993-4.08c1.747 0 3.584 3.801 5.201 7.157c.237.488.477.988.72 1.483c-6.2.197-9.155 1.649-11.559 2.833c-1.759.866-3.147 1.94-5.433 1.94H0v1.574h1.507c2.754 0 4.47-.85 6.295-1.751c2.53-1.243 5.398-2.652 12.157-2.652h3.907V14.5H21.66a1.18 1.18 0 0 1-.972-.393a71 71 0 0 1-1.133-2.321l-.511-1.047s.366-.393 1.38-.393H24Z"
              />
            </svg>
            <div class="text-surface-900 dark:text-surface-0 text-3xl font-medium mb-4">{{ $t(
              'view.auth.login.welcome') }}
            </div>
          </div>

          <div class="mb-8">
            <label for="username"
                   class="block text-surface-900 dark:text-surface-0 text-xl font-medium mb-2">
              {{ $t('view.auth.login.username') }}</label>
            <InputText id="username" type="text"
                       :placeholder="$t('placeholder.input', [$t('view.auth.login.username')])"
                       class="w-full md:w-[30rem] mb-8" v-model="username" />

            <label for="password"
                   class="block text-surface-900 dark:text-surface-0 font-medium text-xl mb-2">
              {{ $t('view.auth.login.password') }}</label>
            <Password id="password" v-model="password"
                      :placeholder="$t('placeholder.input', [$t('view.auth.login.password')])"
                      :toggleMask="true" class="mb-4" fluid :feedback="false"
                      @keydown.enter="signIn"></Password>
          </div>
          <Button :label="$t('view.auth.login.sign-in')" class="w-full" @click="signIn"></Button>
        </div>
      </div>
    </div>
  </div>
</template>
