import * as v from 'valibot';

export const useValidate = () => {
  const errors = ref({});
  const submit = (schema, form, accept) => {
    try {
      errors.value = {};
      v.parse(schema, form);
      accept();
    } catch (error) {
      const errorMap = {};
      for (const issues of error.issues) {
        let message = issues.message;
        if (Array.isArray(issues.message)) {
          message = issues.message.filter(x => x.message).map(x => x.message);
        }
        if (issues.path) {
          for (const path of issues.path) {
            errorMap[path.key] = message;
          }
        } else {
          errorMap['default'] = message;
        }
      }
      errors.value = errorMap;
      console.debug('validate failed', errorMap);
    }
  };
  return { errors, submit };
};
