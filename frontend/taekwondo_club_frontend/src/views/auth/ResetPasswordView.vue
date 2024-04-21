<template>
  <div class="forgot-password-container">
    <el-row :gutter="10" type="flex" justify="center">
      <el-col :lg="8" :md="12" :sm="24">
        <el-card header="Đăng nhập">
          <template #header
            ><h2 class="login-title">Đặt lại mật khẩu</h2></template
          >
          <div v-if="success">
            Đặt lại mật khẩu thành công. Nhấn vào đây để
            <router-link type="primary" :to="Paths.LOGIN"
              >đăng nhập
            </router-link>
            lại.
          </div>
          <el-form
            v-else
            class="login-form"
            :model="formResetPassword"
            :rules="rules"
            :ref="toRef('RESET_PASSWORD_FORM')"
            :size="formSize"
            @submit.native.prevent="submitForm()"
          >
            <el-form-item prop="password">
              <el-input
                type="password"
                v-model="formResetPassword.password"
                placeholder="Mật khẩu mới"
              ></el-input>
            </el-form-item>
            <el-form-item prop="passwordRetype">
              <el-input
                type="password"
                v-model="formResetPassword.passwordRetype"
                placeholder="Nhập lại mật khẩu mới"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <CommonButton
                :ref="toRef('SUBMIT_BTN')"
                type="primary"
                size="large"
                native-type="submit"
                block
                >Gửi
              </CommonButton>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, watch } from 'vue'
import type { FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'

import CommonButton from '@/components/common/CommonButton.vue'
import { resetPassword } from '@/services/auth'
import { useRoute } from 'vue-router'
import { Paths } from '@/router/paths'
import { useAuthenticationStore } from '@/stores/authentication'
import { router } from '@/router'
import { storeToRefs } from 'pinia'
import useRefs from '@/common/useRefs'

const { refs, toRef } = useRefs<{
  SUBMIT_BTN: InstanceType<typeof CommonButton>
  RESET_PASSWORD_FORM: InstanceType<any>
}>()
const formSize = ref('large')
const token = ref('')
const success = ref(false)
const formResetPassword = ref({
  password: '',
  passwordRetype: '',
})

const authenticationStore = useAuthenticationStore()
const { authenticated } = storeToRefs(authenticationStore)

watch(
  authenticated,
  (newAuth) => {
    if (newAuth) {
      router.push(Paths.HOME)
    }
  },
  {
    immediate: true,
  }
)
const $route = useRoute()
onMounted(async () => {
  try {
    if (!$route.query.token) throw new Error()
    else {
      const res = await checkTokenResetPassword($route.query.token)
      if (res.status === 200) token.value = $route.query.token as string
    }
  } catch (e) {
    ElMessage.error({
      dangerouslyUseHTMLString: true,
      message:
        'Yêu cầu đặt lại mật khẩu không hợp lệ!' +
        "<br/>Bạn sẽ được chuyển hướng về <a href='" +
        Paths.HOME +
        "'>Trang chủ</a>.",
    })
    setTimeout(() => {
      location.href = Paths.HOME
    }, 5000)
  }
})

const validatePasswordRetype = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Vui lòng nhập lại mật khẩu'))
  } else if (value !== formResetPassword.value.password) {
    callback(new Error('Mật khẩu nhập lại không khớp!'))
  } else {
    callback()
  }
}

const rules = reactive<FormRules>({
  password: [
    { required: true, message: 'Vui lòng nhập mật khẩu', trigger: 'blur' },
    {
      min: 5,
      message: 'Mật khẩu được nhập phải có ít nhất 5 ký tự',
      trigger: 'blur',
    },
  ],
  passwordRetype: [
    { required: true, message: 'Vui lòng nhập lại mật khẩu', trigger: 'blur' },
    { validator: validatePasswordRetype, trigger: 'blur' },
  ],
})

async function submitForm() {
  refs.SUBMIT_BTN?.setLoading(true)
  try {
    await resetPassword({
      password: formResetPassword.value.password,
      passwordRetype: formResetPassword.value.passwordRetype,
      token: token.value,
    })
    ElMessage.success('Đặt lại mật khẩu thành công!')
    success.value = true
  } catch (e: any) {
    ElMessage.error(e.message)
  } finally {
    refs.SUBMIT_BTN?.setLoading(false)
  }
}
</script>

<style lang="scss" scoped>
.forgot-password-container {
  margin-top: 5rem;
}
</style>
