<script setup>
import { useRouter } from 'vue-router';
import { formatDate } from '@/util/formatter';
import { deleteClientToken, listClientTokens } from '@/service/ClientTokenService';
import ConfirmDeleteButton from '@/components/ConfirmDeleteButton.vue';
import toastUtil from '@/util/toast-util';

const { copy, isSupported } = useClipboard();
const clientTokens = ref([]);
onActivated(() => {
  reload();
});

const reload = () => {
  listClientTokens().then(res => {
    clientTokens.value = res.data;
  });
};

const router = useRouter();
const goDetail = (clientCertificate) => {
  if (clientCertificate) {
    router.push(
      { name: 'client-token-detail', params: { clientCertificateId: clientCertificate.id } });
  } else {
    router.push({ name: 'client-token-new' });
  }
};

const deleteToken = (token) => {
  deleteClientToken(token.id).then(() => {
    toastUtil.success();
    reload();
  });
};

const expirationSeverity = (expiration) => {
  const expirationDate = new Date(expiration);
  const now = new Date();
  const remainingDays = Math.floor((expirationDate - now) / (1000 * 60 * 60 * 24));
  if (remainingDays < 3) {
    return 'danger';
  } else if (remainingDays < 7) {
    return 'warn';
  } else {
    return 'success';
  }
};
</script>
<template>
  <Card>
    <template #title> {{ $t('view.client-token') }}</template>
    <template #content>
      <DataTable :value="clientTokens">
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <Button :label="$t('view.client-token.action.new')" icon="pi pi-plus"
                    @click="goDetail()" />
          </div>
        </template>
        <Column field="name" :header="$t('view.client-token.name')">
          <template #body="{data}">
            <Button link @click="goDetail(data)" :label="data.name"></Button>
          </template>
        </Column>
        <Column field="description" :header="$t('view.client-token.description')"></Column>
        <Column field="role" :header="$t('view.client-token.role')"></Column>
        <Column field="expiration" :header="$t('view.client-token.expiration')">
          <template #body="{data}">
            <Tag v-if="data.expiration" :severity="expirationSeverity(data.expiration)">
              {{ formatDate(data.expiration, 'yyyy-MM-dd') }}
            </Tag>
          </template>
        </Column>
        <Column field="token" :header="$t('view.client-token.token')">
          <template #body="{data}">
            <div class="flex items-center">
              <p class="w-80 truncate">
                {{ data.token }}
              </p>
              <Button icon="pi pi-copy" v-if="isSupported" text @click="copy(data.token)"></Button>
            </div>
          </template>
        </Column>
        <Column field="createdAt" :header="$t('view.client-token.createdAt')">
          <template #body="{data}">
            {{ formatDate(data.createdAt) }}
          </template>
        </Column>
        <Column>
          <template #body="{data}">
            <ConfirmDeleteButton @confirm="deleteToken(data)"></ConfirmDeleteButton>
          </template>
        </Column>
      </DataTable>
    </template>
  </Card>
</template>
