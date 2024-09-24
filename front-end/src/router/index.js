import AppLayout from '@/layout/AppLayout.vue';
import { createRouter, createWebHistory } from 'vue-router';
import { useGlobalStore } from '@/stroes/useGlobalStore';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      name: 'index',
      path: '/',
      component: AppLayout,
      redirect: '/instances',
      children: [
        {
          path: '/instances',
          name: 'instances',
          children: [{
            name: 'instance-list',
            path: '',
            component: () => import('@/views/instances/InstanceList.vue')
          }, {
            name: 'instance-new',
            path: 'new',
            component: () => import('@/views/instances/InstanceDetail.vue')
          }, {
            name: 'instance-detail',
            path: ':instanceId',
            props: true,
            component: () => import('@/views/instances/InstanceDetail.vue')
          }]
        },
        {
          path: '/clusters',
          name: 'clusters',
          redirect: { name: 'cluster-list' },
          children: [
            {
              path: '',
              name: 'cluster-list',
              component: () => import('@/views/clusters/ClusterList.vue')
            },
            {
              path: ':cluster',
              name: 'cluster-detail',
              props: true,
              redirect: { name: 'broker-list' },
              component: () => import('@/views/clusters/ClusterDetail.vue'),
              children: [
                {
                  path: 'brokers',
                  name: 'broker-list',
                  props: true,
                  component: () => import('@/views/brokers/BrokerList.vue')
                },
                {
                  path: 'brokers/:broker',
                  name: 'broker-detail',
                  props: true,
                  component: () => import('@/views/brokers/BrokerDetail.vue')
                }]
            }
          ]
        },
        {
          path: '/tenants',
          name: 'tenants',
          children: [
            {
              path: '',
              name: 'tenant-list',
              component: () => import('@/views/tenants/TenantList.vue')
            },
            {
              path: ':tenant',
              name: 'tenant-detail',
              props: true,
              component: () => import('@/views/tenants/TenantDetail.vue')
            },
            {
              path: 'new',
              name: 'tenant-new',
              component: () => import('@/views/tenants/TenantDetail.vue')
            }
          ]
        },
        {
          path: '/namespaces',
          name: 'namespaces',
          redirect: { name: 'namespace-list' },
          children: [
            {
              path: '',
              name: 'namespace-list',
              component: () => import('@/views/namespaces/NamespaceList.vue')
            },
            {
              path: ':tenant/:namespace',
              name: 'namespace-detail',
              props: true,
              redirect: { name: 'namespace-overview' },
              component: () => import('@/views/namespaces/NamespaceDetail.vue'),
              children: [{
                path: 'overview',
                name: 'namespace-overview',
                props: true,
                component: () => import('@/views/namespaces/NamespaceOverview.vue')
              }, {
                path: 'policies',
                name: 'namespace-policies',
                props: true,
                component: () => import('@/views/namespaces/NamespacePolicies.vue')
              }, {
                path: 'permissions',
                name: 'namespace-permissions',
                props: true,
                component: () => import('@/views/namespaces/NamespacePermissions.vue')
              }, {
                path: 'topics',
                name: 'namespace-topics',
                props: true,
                component: () => import('@/views/components/Topics.vue')
              }, {
                path: 'topics',
                name: 'namespace-analysis',
                props: true,
                component: () => import('@/views/namespaces/NamespaceAnalysis.vue')
              }]
            }
          ]
        },
        {
          path: '/topics',
          name: 'topics',
          children: [
            {
              path: '',
              name: 'topic-list',
              component: () => import('@/views/topics/TopicList.vue')
            },
            {
              path: 'new',
              name: 'topic-new',
              props: route => ({
                tenant: route.query.tenant,
                namespace: route.query.namespace,
                freeMode: route.query.freeMode
                  === 'true'
              }),
              component: () => import('@/views/topics/TopicNew.vue')
            },
            {
              path: ':persistence/:tenant/:namespace/:topic',
              name: 'topic-detail',
              props: true,
              component: () => import('@/views/topics/TopicDetail.vue')
            }
          ]
        },
        {
          path: '/client-certificates',
          name: 'client-certificates',
          children: [{
            name: 'client-certificate-list',
            path: '',
            component: () => import('@/views/client-certificates/ClientCertificateList.vue')
          }, {
            name: 'client-certificate-new',
            path: 'new',
            component: () => import('@/views/client-certificates/ClientCertificateDetail.vue')
          }, {
            name: 'client-certificate-detail',
            path: ':clientCertificateId',
            props: true,
            component: () => import('@/views/client-certificates/ClientCertificateDetail.vue')
          }]
        },
        {
          path: '/client-tokens',
          name: 'client-tokens',
          children: [{
            name: 'client-token-list',
            path: '',
            component: () => import('@/views/client-tokens/ClientTokenList.vue')
          }, {
            name: 'client-token-new',
            path: 'new',
            component: () => import('@/views/client-tokens/ClientTokenDetail.vue')
          }, {
            name: 'client-token-detail',
            path: ':clientTokenId',
            props: true,
            component: () => import('@/views/client-tokens/ClientTokenDetail.vue')
          }]
        },
        {
          path: '/subscription-relationship',
          name: 'subscription-relationship',
          component: () => import('@/views/subscription-relationship/SubscriptionRelationshipList.vue')
        },
        {
          path: '/users',
          name: 'users',
          component: () => import('@/views/users/UserList.vue')
        }
      ]
    },
    {
      path: '/pages',
      name: 'pages',
      children: [{
        path: 'notfound',
        name: 'notfound',
        component: () => import('@/views/pages/NotFound.vue')
      }]
    },
    {
      path: '/auth',
      name: 'auth',
      children: [{
        path: 'login',
        name: 'login',
        component: () => import('@/views/pages/auth/Login.vue')
      }, {
        path: 'access',
        name: 'access',
        component: () => import('@/views/pages/auth/Access.vue')
      }, {
        path: 'error',
        name: 'error',
        component: () => import('@/views/pages/auth/Error.vue')
      }]
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
