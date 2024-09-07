export const arrayOf = (str) => {
  if (str) {
    return str.split(',');
  }
  return [];
};
