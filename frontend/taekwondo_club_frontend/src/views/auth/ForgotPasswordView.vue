<template>
  <div class="login-form-container">
    <el-row :gutter="10" type="flex" justify="center">
      <el-col :lg="8" :md="12" :sm="24">
        <el-card>
          <template #header
            ><h2 class="login-title">Quên mật khẩu</h2></template
          >
          <el-form
            class="login-form"
            :model="formForgotPassword"
            :rules="rules"
            :ref="toRef('FORGOT_PASSWORD_FORM')"
            :size="formSize"
            @submit.native.prevent="submitForm()"
          >
            <el-form-item prop="email">
              <el-input
                v-model="formForgotPassword.email"
                placeholder="Email"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <CommonButton
                :ref="toRef('SUBMIT_BTN')"
                size="large"
                type="primary"
                native-type="submit"
                block
                >Gửi
              </CommonButton>
            </el-form-item>
            <router-link :to="Paths.LOGIN">Đăng nhập</router-link>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'

import CommonButton from '@/components/common/CommonButton.vue'
import { forgotPassword } from '@/services/auth'
import { Paths } from '@/router/paths'
import { useAuthenticationStore } from '@/stores/authentication'
import { router } from '@/router'
import { storeToRefs } from 'pinia'
import { processErrorMessage } from '@/helper/responseErrorHandle'
import useRefs from '@/common/useRefs'

const { refs, toRef } = useRefs<{
  SUBMIT_BTN: InstanceType<typeof CommonButton>
  FORGOT_PASSWORD_FORM: InstanceType<any>
}>()

const formSize = ref('large')

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

const formForgotPassword = ref({
  email: '',
})

const rules = reactive<FormRules>({
  email: [
    {
      required: true,
      message: 'Email là bắt buộc.',
      trigger: 'blur',
    },
    {
      min: 4,
      message: 'Độ dài email phải có ít nhất 5 ký tự.',
      trigger: 'blur',
    },
    {
      type: 'email',
      message: 'Hãy nhập chính xác địa chỉ email',
      trigger: 'blur',
    },
  ],
})

async function submitForm() {
  refs.FORGOT_PASSWORD_FORM.validate(async (valid: boolean) => {
    if (valid) {
      refs.SUBMIT_BTN?.setLoading(true)
      try {
        await forgotPassword(formForgotPassword.value)
        ElMessage.success({
          dangerouslyUseHTMLString: true,
          message:
            'Gửi yêu cầu thành công!<br/>Email xác nhận sẽ được gửi trong giây lát, vui lòng kiểm tra hộp thư và làm theo hướng dẫn.',
          duration: 5000,
          showClose: true,
        })
      } catch (e) {
        processErrorMessage(
          e,
          'Có lỗi xảy ra trong quá trình gửi thông tin quên mật khẩu. Vui lòng thử lại!'
        )
      } finally {
        refs.SUBMIT_BTN?.setLoading(false)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.forgot-password-container {
  margin-top: 5rem;
}
</style>
