<template>
  <el-dialog v-model="show">
    <template #header>
      <h3 class="dialog-header">Thêm người dùng</h3>
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
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" placeholder="Email..." />
        </el-form-item>
        <el-form-item label="Username" prop="username">
          <el-input v-model="form.username" placeholder="Username..." />
        </el-form-item>
        <el-form-item label="Phone Number" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="Phone Number..." />
        </el-form-item>
        <el-form-item label="Birthday" prop="birthday">
          <el-date-picker v-model="form.birthday" type="date" placeholder="Pick a day" />
        </el-form-item>
        <el-form-item label="Birth Place" prop="birthPlace">
          <el-input v-model="form.birthPlace" placeholder="Birth Place..." />
        </el-form-item>
        <el-form-item label="Hobby" prop="hobby">
          <el-input v-model="form.hobby" placeholder="Không..." />
        </el-form-item>
        <el-form-item label="Hatred" prop="hatred">
          <el-input v-model="form.hatred" placeholder="Không..." />
        </el-form-item>
        <el-form-item label="Strength" prop="strength">
          <el-input v-model="form.strength" placeholder="Không..." />
        </el-form-item>
        <el-form-item label="Weakness" prop="weakness">
          <el-input v-model="form.weakness" placeholder="Không..." />
        </el-form-item>
        <el-form-item label="Life Motto" prop="lifeMotto">
          <el-input v-model="form.lifeMotto" placeholder="Không..." />
        </el-form-item>
        <el-form-item label="Achievement" prop="achievement">
          <el-input v-model="form.achievement" placeholder="Không..." />
        </el-form-item>
        <el-form-item label="Join Date" prop="joinDate">
          <el-date-picker v-model="form.joinDate" type="date" placeholder="Pick a day" />
        </el-form-item>
        <el-form-item label="Placement" prop="placement">
          <el-select v-model="form.placement" placeholder="Select placement">
            <el-option label="Đai trắng" value="WHITE"/>
            <el-option label="Đai vàng" value="YELLOW"/>
            <el-option label="Đai xanh lá" value="GREEN"/>
            <el-option label="Đai xanh nước biển" value="BLUE"/>
            <el-option label="Đai đỏ" value="RED"/>
            <el-option label="Đai đen" value="BLACK"/>
          </el-select>
        </el-form-item>
        <el-form-item label="Role" prop="role">
          <el-select v-model="form.role" placeholder="Select role">
            <el-option label="Admin" value="ROLE_ADMIN"/>
            <el-option label="Member" value="ROLE_MEMBER"/>
          </el-select>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="form.password" placeholder="Password..." type="password" />
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" placeholder="Confirm Password..." type="password" />
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <CommonButton @click="resetForm" type="default">Hủy bỏ</CommonButton>
      <CommonButton
          type="primary"
          @click="submitForm"
          :ref="RefNames.SUBMIT_BTN"
      >Lưu lại
      </CommonButton>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'

import type { FormRules } from 'element-plus'
import { processErrorMessage } from '@/helper/responseErrorHandle'
import { ElMessage } from 'element-plus/es'
import CommonButton from '@/components/common/CommonButton.vue'
import {UserService} from "@/services/user";

const show = ref(false)
const form = ref({
  name: '',
  role: '',
  email: '',
  username: '',
  phoneNumber: '',
  birthday: new Date(),
  birthPlace: '',
  hobby: '',
  hatred: '',
  strength: '',
  weakness: '',
  lifeMotto: '',
  achievement: '',
  joinDate: new Date(),
  placement: '',
  password: '',
  confirmPassword: '',
})

const RefNames = {
  MAIN_FORM: 'MAIN_FORM',
  SUBMIT_BTN: 'SUBMIT_BTN',
}

const emit = defineEmits(['close'])

const validateConfirmPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Nhập lại mật khẩu'))
  } else if (value !== form.value.password) {
    callback(new Error('Mật khẩu không khớp'))
  } else {
    callback()
  }
}

const rules = reactive<FormRules>({
  name: [{ required: true, message: 'Nhập tên người dùng' }],
  email: [
    {
      min: 4,
      message: 'Email length should be at least 5 characters',
      trigger: 'blur',
    },
    { required: true, message: 'Hãy nhập email đăng ký', trigger: 'blur' },
    {
      type: 'email',
      message: 'Hãy nhập chính xác địa chỉ email',
      trigger: 'change',
    },
  ],
  password: [
    { required: true, message: 'Hãy nhập mật khẩu ', trigger: 'blur' },
    { min: 8, message: 'Mật khẩu tối thiểu 8 kí tự', trigger: 'change' },
  ],
  confirmPassword: [
    { required: true, message: 'Hãy nhập lại mật khẩu ', trigger: 'change' },
    { min: 8, message: 'Mật khẩu tối thiểu 8 kí tự', trigger: 'change' },
    { validator: validateConfirmPassword, trigger: 'change' },
  ],
})

watch(
    () => show.value,
    (value, oldValue) => {
      if (value != oldValue && value) {
        // $refs[RefNames.MAIN_FORM].resetFields()
      }
    }
)

async function submitForm() {
  try {
    await UserService.add({
      name: form.value.name,
      role: form.value.role,
      email: form.value.email,
      username: form.value.username,
      phoneNumber: form.value.phoneNumber,
      birthday: form.value.birthday,
      birthPlace: form.value.birthPlace,
      hobby: form.value.hobby,
      hatred: form.value.hatred,
      strength: form.value.strength,
      weakness: form.value.weakness,
      lifeMotto: form.value.lifeMotto,
      achievement: form.value.achievement,
      joinDate: form.value.joinDate,
      placement: form.value.placement,
      password: form.value.password,
    })
    ElMessage({
      message: 'Thêm người dùng thành công!',
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
.avatar-image {
  margin: 0 auto;
}

.avatar-image-block {
  display: flex;
}
</style>
