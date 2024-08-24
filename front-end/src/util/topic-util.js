export const simplifyTopic = (topic) => {
  return { topicName: topic.replace(/^persistent:\/\//, '') };
};
export const isSystemTopic = (fullTopic) => {
  if (/.*-RETRY.*/.test(fullTopic)) {
    return true;
  }
  return /.*-DLQ.*/.test(fullTopic);
};

export const deconstructionTopic = (fullTopic) => {
  const regex = /(persistent|non-persistent):\/\/?([^/]+)\/([^/]+)\/(.+)/;

  const match = fullTopic.match(regex);
  const [_, persistence, tenant, namespace, topicName] = match;
  const humanTopicName = topicName.replace(/-partition-\d+/, '');
  return { persistence, tenant, namespace, fullTopic, topicName, humanTopicName };
};

export const mergeTopics = (data) => {
  const topicMap = {};
  data.forEach((fullTopic) => {
    const topic = deconstructionTopic(fullTopic);

    if (!topicMap[topic.humanTopicName]) {
      topicMap[topic.humanTopicName] = { ...topic, children: [topic] };
    } else {
      topicMap[topic.humanTopicName].children.push(topic);
    }
  });
  return Object.values(topicMap);
};
