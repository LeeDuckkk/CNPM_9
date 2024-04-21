<template>
  <span class="common-tag is-round" :style="cssProps">
    <span>
      <slot />
    </span>
  </span>
</template>
<script lang="ts" setup>
import { computed } from 'vue'

const props = withDefaults(
  defineProps<{
    color?: string
    colorBorder?: string
  }>(),
  {
    color: '#409eff',
    colorBorder: '#409eff',
  }
)

const cssProps = computed(() => {
  return {
    '--custom-tag-border-color': props.color,
    '--custom-tag-text-color': props.colorBorder,
  }
})
</script>
<style scoped>
.common-tag.is-round {
  border-radius: 9999px;
}

.common-tag {
  --custom-tag-border-color: #409eff;
  --custom-tag-text-color: #409eff;
  background-color: white;
  border-color: var(--custom-tag-border-color);
  color: var(--custom-tag-text-color);
  display: inline-flex;
  justify-content: center;
  align-items: center;
  min-height: 24px;
  padding: 0 9px;
  line-height: 1.1;
  border-width: 1px;
  border-style: solid;
  box-sizing: border-box;
  --el-icon-size: 14px;
}
</style>
