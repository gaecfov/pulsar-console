import adminApi from '@/util/pulsar-api';

export const listTopics = (tenant, namespace) => {
  return adminApi.get(`/namespaces/${tenant}/${namespace}/topics`, {
    params: {
      mode: 'ALL'
    }
  });
};

export const createNonPartitionedTopic = (persistence, tenant, namespace,
                                          topic) => {
  return adminApi.put(`/${persistence}/${tenant}/${namespace}/${topic}`);
};

export const createPartitionedTopic = (persistence, tenant, namespace, topic,
                                       num) => {
  return adminApi.put(
    `/${persistence}/${tenant}/${namespace}/${topic}/partitions`, num);
};

export const getStats = (topic) => {
  return adminApi.get(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/stats`);
};

export const deleteTopic = (topic) => {
  return adminApi.delete(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}`);
};

export const deletePartitions = (topic) => {
  return adminApi.delete(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/partitions`);
};

export const unload = (topic) => {
  return adminApi.put(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/unload`);
};

export const truncate = (topic) => {
  return adminApi.delete(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/truncate`);
};

export const trim = (topic) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/trim`);
};

export const terminate = (topic) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/terminate`);
};

export const terminatePartitions = (topic) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/terminate/partitions`);
};

export const getPolicy = (topic, policyName, params) => {
  return adminApi.get(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/${policyName}`,
    {
      params
    });
};

export const postPolicy = (topic, policyName, policy, params) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/${policyName}`,
    policy, {
      params
    });
};

export const putPolicy = (topic, policyName, policy, params) => {
  return adminApi.put(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/${policyName}`,
    policy, {
      params
    });
};

export const removePolicy = (topic, policyName, params) => {
  return adminApi.delete(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/${policyName}`, {
      params
    });
};

export const getMetadata = (topic) => {
  return adminApi.get(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.humanTopicName}/internal-info`);
};

export const getInternalStats = (topic) => {
  return adminApi.get(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/internalStats`);
};

export const skipMessages = (topic, subName, numMessages) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/skip/${numMessages}`);
};

export const skipAllMessages = (topic, subName) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/skip_all`);
};

export const expiryAllMessages = (topic, expireTimeInSeconds) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/all_subscription/expireMessages/${expireTimeInSeconds}`);
};

export const expiryMessages = (topic, subName) => {
  return adminApi.post(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/expireMessages`);
};

export const peekMessages = (topic, subName, num) => {
  return adminApi.get(
    `/${topic.persistence}/${topic.tenant}/${topic.namespace}/${topic.topicName}/subscription/${subName}/position/${num}`);
};
