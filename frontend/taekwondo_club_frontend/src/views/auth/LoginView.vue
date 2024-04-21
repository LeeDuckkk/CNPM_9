<template>
  <body>
    <el-card 
    class="w-[640px] h-[550px] mx-auto mt-[5px] bg-white rounded-3xl border border-stone-500 border-opacity-50 flex  justify-center">
      <div class="mt-[20px] flex-col justify-center flex">
        <div class="text-center text-zinc-800 text-[32px] font-medium  font-['Poppins']">Đăng nhập</div>
          
          <el-form
              class="login-form"
              :model="formLogin"
              :rules="rules"
              :ref="toRef('LOGIN_FORM')"
              :size="formSize"
              @submit.native.prevent="loginWithPassword()"
              
          >
          <div class="mt-[40px] flex-col justify-start items-start flex">

            <el-form-item prop="email" class="w-[528px] h-[87px] flex-col justify-start items-start  inline-flex">
              <div class="text-stone-500 text-base font-normal font-['Poppins']">Tài khoản email của bạn:</div>
              <el-input
                  class="w-[528px] h-14 relative"
                  v-model="formLogin.email"
                  placeholder="Email"
              ></el-input>
            </el-form-item>

            <el-form-item prop="password" class="w-[528px] h-[87px] flex-col justify-start items-start  inline-flex">
              <div class="text-stone-500 text-base font-normal font-['Poppins']">Mật khẩu của bạn:</div>
              <el-input
                  class="w-[528px] h-14 relative"
                  v-model="formLogin.password"
                  placeholder="Password"
                  type="password"
              ></el-input>
            </el-form-item>

            <br>
            <el-form-item>
                  <CommonButton
                      :ref="toRef('LOGIN_BTN')"
                      class="w-[528px] h-16 pt-[15px] bg-blue-500 pb-4 rounded-[40px] inline-flex 
                             text-center text-white text-[22px] font-medium font-['Poppins']"
                      size="large"
                      type="primary"
                      native-type="submit"
                      block
                  >Đăng nhập
                  </CommonButton>
                </el-form-item>

          </div>
          
          <el-row >
            <el-col :span="12" class="text-center font-['Poppins']">
              <router-link
                  class="is-underline forgot-password"
                  type="primary"
                  :to="Paths.FORGOT_PASSWORD"
              >Quên mật khẩu?
              </router-link>
            </el-col>
              
              <br />
            <el-col :span="12" class="text-center">
              <router-link
                type="primary"
                :to="Paths.REGISTER"
              >Đăng ký
              </router-link>
            </el-col>
          </el-row>
          
        </el-form>
      </div>
          
    </el-card>
  </body>
</template>

<script lang="ts" setup>
import {
  login,
} from '@/services/auth'
import { Paths } from '@/router/paths'
import { useRoute, useRouter } from 'vue-router'
import { onMounted, reactive, ref, watch } from 'vue'
import { ElMessage, type FormRules } from 'element-plus'

import CommonButton from '@/components/common/CommonButton.vue'
import { useAuthenticationStore } from '@/stores/authentication'
import { storeToRefs } from 'pinia'
import {
  putLocalStorage,
  removeLocalStorage,
} from '@/helper/LocalStorageHelper'
import { LocalStorageKeys } from '@/constants/LocalStorageKey'
import useRefs from '@/common/useRefs'

const { refs, toRef } = useRefs<{
  LOGIN_FORM: InstanceType<any>
  LOGIN_BTN: InstanceType<typeof CommonButton>
}>()
const $route = useRoute()
const $router = useRouter()
const formLogin = ref({
  email: '',
  password: '',
})
const formSize = ref('large')
const rules = reactive<FormRules>({
  email: [
    {
      required: true,
      message: 'Username is required',
      trigger: 'blur',
    },
    {
      min: 4,
      message: 'Username length should be at least 5 characters',
      trigger: 'blur',
    },
  ],
  password: [
    { required: true, message: 'Vui lòng nhập mật khẩu', trigger: 'blur' },
    {
      min: 2,
      message: 'Mật khẩu được nhập phải có ít nhất 2 ký tự',
      trigger: 'blur',
    },
  ],
})

const authenticationStore = useAuthenticationStore()
const { authenticated } = storeToRefs(authenticationStore)

const router = useRouter()
const route = useRoute()

async function loginWithPassword() {
  refs.LOGIN_FORM.validate(async (valid: any) => {
    if (valid) {
      refs.LOGIN_BTN?.setLoading(true)
      try {
        const loginRes = await login(
            {
              username: formLogin.value.email,
              password: formLogin.value.password,
            })
        // console.log(loginRes)
        putLocalStorage(LocalStorageKeys.AUTHENTICATION_TOKEN, loginRes.data.accessToken)
        if ($route.query.redirectUrl) {
          location.href = $route.query.redirectUrl as string
        } else {
          await router.push(Paths.MAIN)
        }
      } catch (e: any) {
        ElMessage.error(
            e.response?.data?.data
                ? e.response?.data?.data
                : 'Sai thông tin đăng nhập. Vui lòng nhập lại...'
        )
      } finally {
        refs.LOGIN_BTN?.setLoading(false)
      }
    }
  })
}

// function loginWithGoogle() {
//   location.href = getGoogleLoginUrl() + '&authCode=' + route.query.authCode
// }
//
// function loginWithFacebook() {
//   location.href = getGoogleFacebookUrl() + '&authCode=' + route.query.authCode
// }
</script>

<style lang="scss" scoped>
$white: #fff;
$google-blue: #409eff;


body {
  background:linear-gradient(to right, #017a6e, #0dc2af);
  display: flex;
  align-items: center;
  
}

.google-btn {
  cursor: pointer;
  width: 100%;
  height: 42px;
  background-color: $google-blue;
  border-radius: 2px;
  box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.25);

  .google-icon-wrapper {
    position: absolute;
    margin-top: 1px;
    margin-left: 1px;
    width: 40px;
    height: 40px;
    border-radius: 2px;
    background-color: $white;
  }

  .google-icon {
    position: absolute;
    margin-top: 11px;
    margin-left: 11px;
    width: 18px;
    height: 18px;
  }
}


</style>

<style lang="scss">
.login-title {
  margin-top: 0;
  margin-bottom: 0;
}



.router-link-custom {
  text-decoration: none;
}
</style>
