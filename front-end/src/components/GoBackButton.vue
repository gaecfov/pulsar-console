<script setup>
const props = defineProps(['route']);
const router = useRouter();
const goBack = () => {
  if (props.route) {
    router.push({
      name: props.route
    });
  }else{
    router.back();
  }
};
</script>
<template>
  <Button type="button" icon="pi pi-arrow-left" severity="secondary" v-tooltip="$t('action.go-back')"
          @click="goBack"></Button>
</template>
