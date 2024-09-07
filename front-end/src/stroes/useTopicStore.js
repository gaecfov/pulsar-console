import * as ts from '@/service/TopicService';
import toast from '@/util/toast-util';
import { mergeTopics } from '@/util/topic-util';

const useTopic = () => {
  const tenant = ref();
  const namespace = ref();
  const topics = ref([]);
  const loading = ref(false);
  const reload = () => {
    if (tenant.value && namespace.value) {
      loading.value = true;
      ts.listTopics(tenant.value, namespace.value).then((res) => {
        topics.value = mergeTopics(res.data);
        loading.value = false;
      });
    } else {
      topics.value = [];
    }
  };

  watch([() => namespace.value], () => {
    reload();
  });

  const createPartitionedTopic = (persistence, tenant, namespace, topic, num) => {
    return ts.createPartitionedTopic(persistence, tenant, namespace, topic, num).then(() => {
      toast.success();
      reload();
    });
  };
  const createNonPartitionedTopic = (persistence, tenant, namespace, topic) => {
    return ts.createNonPartitionedTopic(persistence, tenant, namespace, topic).then(() => {
      toast.success();
      reload();
    });
  };

  const deleteTopic = (topic) => {
    return ts.deleteTopic(topic).then(() => {
      toast.success();
      reload();
    });
  };

  return {
    tenant,
    namespace,
    topics,
    reload,
    loading,
    createPartitionedTopic,
    createNonPartitionedTopic,
    deleteTopic
  };
};

export const useTopicStore = defineStore('topic-store', () => {
  return useTopic();
});
