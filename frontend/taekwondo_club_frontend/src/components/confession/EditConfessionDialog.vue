<template>
  <el-dialog v-model="show">
    <template #header>
      <h3 class="dialog-header">Chỉnh sửa confession</h3>
      <br>
      <el-form
          label-position="left"
          label-width="150px"
          :model="form"
          :ref="RefNames.MAIN_FORM"
          :rules="rules"
          style="max-width: 650px"
      >
        <el-form-item label="Image" prop="image">
          <el-input v-model="form.image" placeholder="Image URL..." />
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" placeholder="Name..." />
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input v-model="form.description" placeholder="Description..." />
        </el-form-item>
        <el-form-item label="Content" prop="content">
          <el-input v-model="form.content" placeholder="Content..." />
        </el-form-item>
        <el-form-item label="Author" prop="author">
          <el-input v-model="form.author" placeholder="Author..." />
        </el-form-item>
        <el-form-item label="Is Approved" prop="isApproved">
          <el-checkbox v-model="form.isApproved">Is Approved</el-checkbox>
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
import {defineEmits, defineProps, onMounted, reactive, ref, watch} from 'vue'
import type {FormRules} from 'element-plus'
import {processErrorMessage} from '@/helper/responseErrorHandle'
import {ElMessage} from 'element-plus/es'
import CommonButton from '@/components/common/CommonButton.vue'
import {ConfessionService} from "@/services/confession";

const show = ref(false)
const form = ref({
  image: '',
  name: '',
  description: '',
  content: '',
  author: '',
  isApproved: false,
})

const RefNames = {
  MAIN_FORM: 'MAIN_FORM',
  SUBMIT_BTN: 'SUBMIT_BTN',
}

const emit = defineEmits(['close'])

const rules = reactive<FormRules>({
  image: [{ required: true, message: 'Nhập URL hình ảnh' }],
  name: [{ required: true, message: 'Nhập tên' }],
  description: [{ required: true, message: 'Nhập mô tả' }],
  content: [{ required: true, message: 'Nhập nội dung' }],
  author: [{ required: true, message: 'Nhập tên tác giả' }],
})

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
})

watch(
    () => props.id,
    async () => {
      await refreshData()
    }
)

onMounted(async () => {
  await refreshData()
})

async function refreshData() {
  try {
    if (!props.id) return
    const confession = await ConfessionService.getById(props.id)
    form.value = {
      image: confession.image,
      name: confession.name,
      description: confession.description,
      content: confession.content,
      author: confession.author,
      isApproved: confession.isApproved,
    }
  } catch (e) {
    processErrorMessage(e)
  }
}

async function submitForm() {
  try {
    await ConfessionService.edit(props.id, {
      image: form.value.image,
      name: form.value.name,
      description: form.value.description,
      content: form.value.content,
      author: form.value.author,
      isApproved: form.value.isApproved,
    })
    ElMessage({
      message: 'Cập nhật lời thú nhận thành công!',
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