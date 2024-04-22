<template>
  <section id="login-form">
    <div class="container_3d">
      <div class="wrapper">
        <el-form
            class="form"
            :model="formForgotPassword"
            :rules="rules"
            :ref="toRef(RefName.FORGOT_PASSWORD_FORM)"
            @submit.native.prevent="submitForm()">
          <h3>Quên mật khẩu</h3>
          <div class="flex-column">
            <label>Email </label>
          </div>
          <div class="inputForm">
            <svg height="20" viewBox="0 0 32 32" width="20" xmlns="http://www.w3.org/2000/svg">
              <g id="Layer_3" data-name="Layer 3">
                <path
                    d="m30.853 13.87a15 15 0 0 0 -29.729 4.082 15.1 15.1 0 0 0 12.876 12.918 15.6 15.6 0 0 0 2.016.13 14.85 14.85 0 0 0 7.715-2.145 1 1 0 1 0 -1.031-1.711 13.007 13.007 0 1 1 5.458-6.529 2.149 2.149 0 0 1 -4.158-.759v-10.856a1 1 0 0 0 -2 0v1.726a8 8 0 1 0 .2 10.325 4.135 4.135 0 0 0 7.83.274 15.2 15.2 0 0 0 .823-7.455zm-14.853 8.13a6 6 0 1 1 6-6 6.006 6.006 0 0 1 -6 6z">
                </path>
              </g>
            </svg>

            <input type="text" class="input"
                   id="exampleInputEmail" aria-describedby="emailHelp"
                   placeholder="Nhập địa chỉ email"
                  v-model="formForgotPassword.email">

          </div>

          <button class="button-submit" type="submit">Đặt lại mật khẩu</button>
          <p class="p">Nhớ lại mật khẩu ? <span class="span">
            <router-link
                type="primary"
                :to="Paths.LOGIN"
            >Đăng nhập
          </router-link>
          </span></p>
        </el-form>
      </div>
    </div>
  </section>
</template>

<script lang="ts" setup>
import {reactive, ref, toRef, watch} from 'vue'
import type { FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { forgotPassword } from '@/services/auth'
import { Paths } from '@/router/paths'
import { useAuthenticationStore } from '@/stores/authentication'
import { router } from '@/router'
import { storeToRefs } from 'pinia'
import { processErrorMessage } from '@/helper/responseErrorHandle'
import useRefs from '@/common/useRefs'

const RefName = {
  FORGOT_PASSWORD_FORM: 'FORGOT_PASSWORD_FORM',
  SUBMIT_BTN: 'SUBMIT_BTN',
}

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

      try {
        await forgotPassword(formForgotPassword.value)
        await router.push(Paths.LOGIN)
        ElMessage.success({
          dangerouslyUseHTMLString: true,
          message:
            'Gửi yêu cầu thành công!<br/>Email xác nhận sẽ được gửi trong giây lát, vui lòng kiểm tra hộp thư và lấy mật khẩu mới.',
          duration: 5000,
          showClose: true,
        })
      } catch (e) {
        processErrorMessage(
          e,
          'Có lỗi xảy ra trong quá trình gửi thông tin quên mật khẩu. Vui lòng thử lại!'
        )
      }
}
</script>

<style lang="scss" scoped>

.login-title {
  margin-top: 0;
  margin-bottom: 0;
}

.router-link-custom {
  text-decoration: none;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  background-color: #ffffff;
  padding: 30px;
  width: 450px;
  border-radius: 20px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

::placeholder {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

.form button {
  align-self: flex-end;
}

.flex-column > label {
  color: #151717;
  font-weight: 600;
}

.inputForm {
  border: 1.5px solid #ecedec;
  border-radius: 10px;
  height: 50px;
  display: flex;
  align-items: center;
  padding-left: 10px;
  transition: 0.2s ease-in-out;
}

.input {
  margin-left: 10px;
  border-radius: 10px;
  border: none;
  width: 85%;
}

.input:focus {
  outline: none;
}

.inputForm:focus-within {
  border: 1.5px solid #2d79f3;
}

.flex-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
  justify-content: space-between;
}

.flex-row > div > label {
  font-size: 14px;
  color: black;
  font-weight: 400;
}

.span {
  font-size: 14px;
  margin-left: 5px;
  color: #2d79f3;
  font-weight: 500;
  cursor: pointer;
}

.button-submit {
  margin: 20px 0 10px 0;
  background-color: #151717;
  border: none;
  color: white;
  font-size: 15px;
  font-weight: 500;
  border-radius: 10px;
  height: 50px;
  width: 100%;
  cursor: pointer;
}

.button-submit:hover {
  background-color: #252727;
}

.button-resend {
  margin: 20px 0 10px 0;
  background-color: #ffffff;
  border: 1px solid #2d79f3;
  color: #2d79f3;
  font-size: 15px;
  font-weight: 500;
  border-radius: 10px;
  height: 50px;
  width: 100%;
  cursor: pointer;
}

.p {
  text-align: center;
  color: black;
  font-size: 14px;
  margin: 5px 0;
}

.btn {
  margin-top: 10px;
  width: 100%;
  height: 50px;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 500;
  gap: 10px;
  border: 1px solid #ededef;
  background-color: white;
  cursor: pointer;
  transition: 0.2s ease-in-out;
}

.btn:hover {
  border: 1px solid #2d79f3;;
}

.container_3d {
  overflow: auto;
  width: 100%;
  height: 100%;
  --s: 82px;
  --c1: #b2b2b2;
  --c2: #ffffff;
  --c3: #d9d9d9;

  --_g: var(--c3) 0 120deg, #0000 0;
  background: conic-gradient(from -60deg at 50% calc(100% / 3), var(--_g)),
  conic-gradient(from 120deg at 50% calc(200% / 3), var(--_g)),
  conic-gradient(from 60deg at calc(200% / 3),
          var(--c3) 60deg,
          var(--c2) 0 120deg,
          #0000 0),
  conic-gradient(from 180deg at calc(100% / 3), var(--c1) 60deg, var(--_g)),
  linear-gradient(90deg,
          var(--c1) calc(100% / 6),
          var(--c2) 0 50%,
          var(--c1) 0 calc(500% / 6),
          var(--c2) 0);
  background-size: calc(1.732 * var(--s)) var(--s);
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

body {
  background-color: rgb(192, 211, 211);
}

</style>
