import mitt from 'mitt';

const emitter = mitt();
export const useEmitter = () => {
  return emitter;
};
