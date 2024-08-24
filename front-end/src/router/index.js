import AppLayout from '@/layout/AppLayout.vue';
import { createRouter, createWebHistory } from 'vue-router';
import { useGlobalStore } from '@/stroes/useGlobalStore';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: AppLayout,
      redirect: '/instance',
      children: [
        {
          path: '/instance',
          name: 'instance',
          component: () => import('@/views/instance/InstanceList.vue')
        },
        {
          path: '/cluster',
          name: 'cluster',
          component: () => import('@/views/cluster/ClusterList.vue')
        },
        {
          path: '/broker',
          name: 'broker',
          component: () => import('@/views/broker/BrokerList.vue')
        },
        {
          path: '/tenant',
          name: 'tenant',
          component: () => import('@/views/tenant/TenantList.vue')
        },
        {
          path: '/namespace',
          name: 'namespace',
          component: () => import('@/views/namespace/NamespaceList.vue')
        },
        {
          path: '/topic',
          name: 'topic',
          component: () => import('@/views/topic/TopicList.vue')
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('@/views/user/UserList.vue')
        }
      ]
    },
    {
      path: '/pages/notfound',
      name: 'notfound',
      component: () => import('@/views/pages/NotFound.vue')
    },

    {
      path: '/auth/login',
      name: 'login',
      component: () => import('@/views/pages/auth/Login.vue')
    },
    {
      path: '/auth/access',
      name: 'accessDenied',
      component: () => import('@/views/pages/auth/Access.vue')
    },
    {
      path: '/auth/error',
      name: 'error',
      component: () => import('@/views/pages/auth/Error.vue')
    }
  ]
});

router.beforeEach((to, from, next) => {
  const store = useGlobalStore();
  if (to.name !== 'login' && !store.isLogin) {
    next({ name: 'login' });
  } else {
    next();
  }
});

export default router;
