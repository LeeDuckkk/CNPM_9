<template>
  <el-dialog v-model="show">
    <template #header>
      <h3 class="dialog-header">Chỉnh sửa kỹ năng</h3>
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
        <el-form-item label="Description" prop="description">
          <el-input v-model="form.description" placeholder="Description..." />
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
import { reactive, ref, watch, onMounted, defineEmits, defineProps } from 'vue'
import type { FormRules } from 'element-plus'
import { processErrorMessage } from '@/helper/responseErrorHandle'
import { ElMessage } from 'element-plus/es'
import CommonButton from '@/components/common/CommonButton.vue'
import {SkillService} from "@/services/skill";

const show = ref(false)
const form = ref({
  name: '',
  description: '',
})

const RefNames = {
  MAIN_FORM: 'MAIN_FORM',
  SUBMIT_BTN: 'SUBMIT_BTN',
}

const emit = defineEmits(['close'])

const rules = reactive<FormRules>({
  name: [{ required: true, message: 'Nhập tên kỹ năng' }],
  description: [{ required: true, message: 'Nhập mô tả kỹ năng' }],
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
    const response = await SkillService.getOne(props.id)
    const skill = response?.data
    form.value = {
      name: skill.name,
      description: skill.description,
    }
  } catch (e) {
    processErrorMessage(e)
  }
}

async function submitForm() {
  try {
    await SkillService.edit(props.id, {
      name: form.value.name,
      description: form.value.description,
    })
    ElMessage({
      message: 'Cập nhật kỹ năng thành công!',
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