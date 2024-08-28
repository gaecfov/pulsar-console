import pulsar from './pulsar.json';
import brokerConf from './pulsar-broker-conf.json';
import menu from './menu.json';
import views from './views.json';
import dictionary from './dictionary.json';

export default {
  ...pulsar,
  ...brokerConf,
  ...menu,
  ...dictionary,
  ...views
};
