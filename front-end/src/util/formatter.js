import { isNull } from '@/util/assert';

export const formatDate = (isoString, format = 'yyyy-MM-dd HH:mm:ss') => {
  if (isNull((isoString))) {
    return null;
  }
  const date = new Date(isoString);

  const map = {
    'yyyy': date.getFullYear(),
    'MM': String(date.getMonth() + 1).padStart(2, '0'),
    'dd': String(date.getDate()).padStart(2, '0'),
    'HH': String(date.getHours()).padStart(2, '0'),
    'mm': String(date.getMinutes()).padStart(2, '0'),
    'ss': String(date.getSeconds()).padStart(2, '0')
  };

  return format.replace(/yyyy|MM|dd|HH|mm|ss/g, (matched) => map[matched]);
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
