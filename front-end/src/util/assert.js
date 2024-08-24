export const isNull = (val) => {
  return val === undefined || val === null;
};

export const isBlank = (str) => {
  return isNull(str) || str.trim() === '';
};
