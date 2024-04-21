<template>
  <el-button
    :type="type"
    :size="size"
    :loading="loading"
    :disabled="disabled"
    :title="title"
  >
    <template #loading>
      <slot name="loading" v-if="!slots.loadingIconOnly"></slot>
    </template>
    <slot name="loadingIconOnly" v-if="loading && slots.loadingIconOnly"></slot>
    <slot v-else />
  </el-button>
</template>

<script lang="ts" setup>
import { ref, useSlots } from 'vue'

const slots = useSlots()

withDefaults(
  defineProps<{
    type?: string
    size?: string
    disabled?: boolean
    title?: string
  }>(),
  {
    type: 'primary',
    size: 'default',
    disabled: false,
  }
)
const loading = ref(false)

function setLoading(_loading: boolean) {
  loading.value = _loading
}

defineExpose({
  setLoading,
})
</script>