import adminApi from '@/util/pulsar-api';

export const listTopics = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/topics`, {
    params: {
      mode: 'ALL'
    }
  });
};

export const createNonPartitionedTopic = (persistence, tenant, namespace, topic) => {
  return adminApi.put(`/${persistence}/${tenant}/${namespace}/${topic}`);
};

export const createPartitionedTopic = (persistence, tenant, namespace, topic, num) => {
  return adminApi.put(`/${persistence}/${tenant}/${namespace}/${topic}/partitions`, num);
};

export const getTopicStats = (topic) => {
  return adminApi.get(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/stats`);
};

export const deleteTopic = (topic) => {
  return adminApi.delete(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}`);
};

export const unloadTopic = (topic) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/unload`);
};

export const truncateTopic = (topic) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/truncate`);
};

export const getTopicTTLSeconds = (topic) => {
  return adminApi.get(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/messageTTL`);
};

export const setTopicTTLSeconds = (topic, messageTTL) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/messageTTL`, undefined, {
    params: {
      messageTTL
    }
  });
};
export const clearTopicTTLSeconds = (topic) => {
  return adminApi.delete(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/messageTTL`);
};

export const getTopicRetentionConfiguration = (topic) => {
  return adminApi.get(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/retention`);
};

export const setTopicRetentionConfiguration = (topic, retentionPolicy) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/retention`, retentionPolicy);
};

export const clearTopicRetentionConfiguration = (topic) => {
  return adminApi.delete(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/retention`);
};

export const getTopicPersistenceConfiguration = (topic) => {
  return adminApi.get(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/persistence`);
};

export const setTopicPersistenceConfiguration = (topic,persistencePolicy) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/persistence`,persistencePolicy);
};

export const clearTopicPersistenceConfiguration = (topic) => {
  return adminApi.delete(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/persistence`);
};

export const skipMessage = (topic, subName, numMessages) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/skip/${numMessages}`);
};

export const skipAllMessage = (topic, subName) => {
  return adminApi.post(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/skip_all`);
};

export const peekMessages = (topic, subName,num) => {
  return adminApi.get(`/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/position/${num}`);
};
