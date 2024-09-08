import { getLeaderBroker, listBrokers } from '@/service/BrokerService';

export const useBrokerStore = defineStore('broker-store', () => {
  const cluster = ref();
  const brokers = ref([]);
  const leaderBroker = ref();
  const leaderBrokerId = computed(() => {
    return leaderBroker.value ? leaderBroker.value.brokerId : null;
  });

  const reload = () => {
    listBrokers().then((rsp) => {
      brokers.value = rsp.data.map((item) => {
        return {
          brokerId: item,
          leader: false
        };
      });
    });
    getLeaderBroker().then((res) => {
      leaderBroker.value = res.data;
    });
  };

  return {
    cluster,
    brokers,
    leaderBroker,
    leaderBrokerId,
    reload
  };
});
