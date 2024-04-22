<template>
  <el-dialog v-model="show">
    <template #header>
      <h3 class="dialog-header">Thêm confession</h3>
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
          <el-upload
              ref="imageFile"
              class="image-uploader"
              action=""
              :limit="1"
              :beforeUpload="beforeImageUpload"
              :on-change="handleImageChange"
              :auto-upload="false"
          >
            <template #trigger>
              <el-button size="small" type="primary">Upload Image</el-button>
            </template>
          </el-upload>
        </el-form-item>

        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" placeholder="Name..."/>
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input v-model="form.description" placeholder="Description..."/>
        </el-form-item>
        <el-form-item label="Content" prop="content">
          <el-input v-model="form.content" placeholder="Content..."/>
        </el-form-item>
        <el-form-item label="Author" prop="author">
          <el-input v-model="form.author" placeholder="Author..."/>
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
import {defineEmits, reactive, ref} from 'vue'
import type {FormRules, UploadFile} from 'element-plus'
import {processErrorMessage} from '@/helper/responseErrorHandle'
import {ElMessage} from 'element-plus/es'
import CommonButton from '@/components/common/CommonButton.vue'
import {ConfessionService} from "@/services/confession";

let imageFile = ref(null as unknown as string | Blob)
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
  image: [{required: true, message: 'Nhập URL hình ảnh'}],
  name: [{required: true, message: 'Nhập tên'}],
  description: [{required: true, message: 'Nhập mô tả'}],
  content: [{required: true, message: 'Nhập nội dung'}],
  author: [{required: true, message: 'Nhập tên tác giả'}],
})

async function submitForm() {
  try {
    const formData = new FormData();
    formData.append('name', form.value.name);
    formData.append('image', imageFile.value as string | Blob);
    formData.append('description', form.value.description);
    formData.append('content', form.value.content);
    formData.append('author', form.value.author);
    formData.append('isApproved', form.value.isApproved ? 'true' : 'false');

    await ConfessionService.add(formData)
    ElMessage({
      message: 'Thêm lời thú nhận thành công!',
      type: 'success',
      duration: 5000,
    })
    emit('close')
  } catch (e: any) {
    processErrorMessage(e)
  }
}

function beforeImageUpload(file: any) {
  const isJPG = file.type === 'image/jpeg';
  const isPNG = file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG && !isPNG) {
    ElMessage.error('Chỉ chấp nhận file JPG/PNG!');
  }
  if (!isLt2M) {
    ElMessage.error('Kích thước file phải nhỏ hơn 2MB!');
  }
  return false;
}

function handleImageChange(file: UploadFile) {
  imageFile.value = file.raw as string | Blob
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