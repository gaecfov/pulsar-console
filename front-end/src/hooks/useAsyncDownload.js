const parseFileName = (response, defaultFileName) => {
  const contentDisposition = response.headers['content-disposition'];
  let fileName = defaultFileName;

  if (contentDisposition && contentDisposition.includes('filename')) {
    const fileNameMatch = contentDisposition.match(
      /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
    if (fileNameMatch.length > 1) {
      fileName = fileNameMatch[1].replace(/['"]/g, '');
    }
  }
  return fileName;
};

export const useAsyncDownload = () => {
  const downloadBlob = (response, fileName = 'temp') => {
    const fileBlob = new Blob([response.data], {
      type: response.headers['content-type']
    });

    const downloadLink = document.createElement('a');
    downloadLink.href = URL.createObjectURL(fileBlob);
    downloadLink.download = parseFileName(response, fileName);
    downloadLink.click();

    URL.revokeObjectURL(downloadLink.href);
  };
  return { downloadBlob };
};
