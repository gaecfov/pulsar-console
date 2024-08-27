export const isNull = (val) => {
  return val === undefined || val === null;
};

export const isBlank = (str) => {
  return isNull(str) || str.trim() === '';
};

export const isEmpty = (value) => {
  if (isNull(value)) {
    return true;
  }
  if (typeof value === 'string' || Array.isArray(value)) {
    return value.length === 0;
  }

  if (typeof value === 'object') {
    if (value instanceof Map || value instanceof Set) {
      return value.size === 0; // 检查空的 Map 和 Set
    }
    return Object.keys(value).length === 0; // 检查空对象
  }
  return false;
};
