<template>
  <el-dialog v-model="show">
    <template #header>
      <h3 class="dialog-header">Thêm sự kiện</h3>
      <br>
      <el-form
          label-position="left"
          label-width="150px"
          :model="form"
          :ref="RefNames.MAIN_FORM"
          :rules="rules"
          style="max-width: 650px"
      >
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" placeholder="Name..." />
        </el-form-item>
        <el-form-item label="Content" prop="content">
          <el-input v-model="form.content" placeholder="Content..." />
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <CommonButton @click="resetForm" type="default">Cancel</CommonButton>
      <CommonButton
          type="primary"
          @click="submitForm"
          :ref="RefNames.SUBMIT_BTN"
      >Save
      </CommonButton>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref, defineEmits } from 'vue'
import type { FormRules } from 'element-plus'
import { processErrorMessage } from '@/helper/responseErrorHandle'
import { ElMessage } from 'element-plus/es'
import CommonButton from '@/components/common/CommonButton.vue'
import {EventService} from "@/services/event";

const show = ref(false)
const form = ref({
  name: '',
  content: '',
})

const RefNames = {
  MAIN_FORM: 'MAIN_FORM',
  SUBMIT_BTN: 'SUBMIT_BTN',
}

const emit = defineEmits(['close'])

const rules = reactive<FormRules>({
  name: [{ required: true, message: 'Nhập tên sự kiện' }],
  content: [{ required: true, message: 'Nhập nội dung sự kiện' }],
})

async function submitForm() {
  try {
    await EventService.add({
      name: form.value.name,
      content: form.value.content,
    })
    ElMessage({
      message: 'Thêm sự kiện thành công!',
      type: 'success',
      duration: 5000,
    })
    emit('close')
  } catch (e: any) {
    processErrorMessage(e)
  }
}

function openModal() {
  show.value = true
}

function closeModal() {
  show.value = false
}

function resetForm() {
  show.value = false
  emit('close')
}

defineExpose({
  openModal,
  closeModal,
})
</script>

<style lang="scss">
/* The styles go here, similar to AddUserDialog */
</style>