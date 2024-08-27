export const deconstructionNamespace = (fullNamespace) => {
  const arr = fullNamespace.split('/');
  return { tenant: arr[0], namespace: arr[1] };
};

export const analyseTopic = (topic, stats) => {
  const subscriptions = stats.subscriptions;
  const result = {
    backlogs: []
  };
  if (subscriptions) {
    Object.keys(subscriptions).forEach((k) => {
      const backlog = subscriptions[k].msgBacklog;
      if (backlog > 0) {
        result.backlogs.push({
          topic: topic,
          subscription: k,
          backlog: backlog
        });
      }
    });
  }
  if (stats.storageSize > 0) {
    result.storageSize = {
      topic: topic,
      storageSize: stats.storageSize
    };
  }
  return result;
};
