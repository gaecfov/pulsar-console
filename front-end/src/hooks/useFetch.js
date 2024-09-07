export const useFetch = (fetch, map) => {
  const list = ref([]);

  const reload = () => {
    fetch().then((res) => {
      if (map) {
        list.value = map(res.data);
      } else {
        list.value = res.data;
      }
    })
  }

  return {list, reload}
}
