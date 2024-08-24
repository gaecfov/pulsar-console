export const formatDate = (isoString) => {
  const date = new Date(isoString);

  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

export const fixedNum = (num, decimalPlaces = 2) => {
  if (num) {
    return num.toFixed(decimalPlaces);
  }
  return 0;
};

export const formatRate = (num, unit = 's', decimalPlaces = 2) => {
  return `${fixedNum(num, decimalPlaces)}/${unit}`;
};

export const formatThroughput = (bytes, unit = 's') => {
  return `${formatStorageSize(bytes)}/${unit}`;
};

export const formatStorageSize = (bytes) => {
  if (bytes === 0) {
    return '0 B';
  }
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  const value = bytes / Math.pow(k, i);
  return `${value.toFixed(2)} ${sizes[i]}`;
};
