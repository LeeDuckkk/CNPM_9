<template>
  <el-row type="flex" justify="center" v-if="authenticated">
    <el-col :xs="24" :sm="0">
<!--      <NavigationMobile />-->
    </el-col>
    <el-col :span="4" class="hidden-xs-only">
      <NavigationDesktop />
    </el-col>
    <el-col :xs="24" :sm="20" class="main-content">
      <RouterView />
    </el-col>
  </el-row>
  <div class="desktop-logo-container" v-else>
    <img src="@/assets/images/logo.svg" class="desktop-logo" />
  </div>
</template>

<script lang="ts" setup>

import { useAuthenticationStore } from '@/stores/authentication'
import { storeToRefs } from 'pinia'
import NavigationDesktop from "@/layouts/main/NavigationDesktop.vue";

const authenticationStore = useAuthenticationStore()

authenticationStore.loadFromServer()
const { authenticated, roleList } = storeToRefs(authenticationStore)
</script>

<style lang="scss" scoped>
.main-content {
  min-height: 600px;
  padding: 0.5rem 1.5rem;
}
</style>
