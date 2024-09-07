const pulsarFiles = import.meta.glob('./pulsar/*.json', {eager: true});
const viewsFiles = import.meta.glob('./views/*.json', {eager: true});

function mergeJson(modules) {
  let obj = {};
  for (const path in modules) {
    obj = {...obj, ...modules[path].default};
  }
  return obj;
}

const pulsar = mergeJson(pulsarFiles);
const views = mergeJson(viewsFiles);
export default {...pulsar, ...views};
