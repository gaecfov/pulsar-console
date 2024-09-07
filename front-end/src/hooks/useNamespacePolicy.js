import * as ns from '@/service/NamespaceService';
import toastUtil from '@/util/toast-util';
import { useEmitter } from '@/hooks/useEmitter';

const emitter = useEmitter();
export const useNamespacePolicy = (policy, getValue, initValue, initParams) => {

  const tenant = inject('tenant');
  const namespace = inject('namespace');
  const policies = inject('namespace-policies');
  const policeName = policy;

  const policyValue = ref();
  const policyParams = ref();

  if (initParams) {
    policyParams.value = initParams();
  }

  const policyDisplay = ref();

  watchEffect(() => {
    if (getValue) {
      policyDisplay.value = getValue(policies.value);
    }
    if (initValue) {
      const value = initValue(policyDisplay.value, policies.value);
      if (typeof value === 'object') {
        policyValue.value = { ...value };
      } else if (Array.isArray(value)) {
        policyValue.value = [...value];
      }
      policyValue.value = value;
    }
  });

  const postPolicy = (closeCallback) => {
    ns.postPolicy(tenant.value, namespace.value, policeName, policyValue.value,
      policyParams.value).then(() => {
      toastUtil.success();
      emitter.emit('namespace-policy-changed', policeName);
      closeCallback();
    });
  };

  const putPolicy = (closeCallback) => {
    ns.putPolicy(tenant.value, namespace.value, policeName, policyValue.value,
      policyParams.value).then(() => {
      toastUtil.success();
      emitter.emit('namespace-policy-changed', policeName);
      closeCallback();
    });
  };

  const removePolicy = (closeCallback) => {
    ns.removePolicy(tenant.value, namespace.value, policeName).then(() => {
      toastUtil.success();
      emitter.emit('namespace-policy-changed', policeName);
      closeCallback();
    });
  };

  return {
    tenant,
    namespace,
    policies,
    policeName,
    policyDisplay,
    policyValue,
    postPolicy,
    putPolicy,
    removePolicy
  };
};
