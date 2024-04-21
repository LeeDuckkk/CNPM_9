<template>
  <RouterView />
</template>

<script lang="ts" setup>
import { useAuthenticationStore } from '@/stores/authentication'

const authenticationStore = useAuthenticationStore()

authenticationStore.loadFromServer()
</script>

<style lang="scss" scoped></style>
