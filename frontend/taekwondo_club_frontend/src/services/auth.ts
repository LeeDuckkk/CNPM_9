import http, {clearAuthToken} from '@/services/http'
import { AuthAPI } from '@/constants/API'
import {ANONYMOUS_ALLOW_PATHS, Paths} from "@/router/paths";

export const getUserInfo = async () => {
    const response = await http.get(AuthAPI.GET_USER_INFO)
    return response.data
}
export const register = async (payload: any) => {
    return await http.post(AuthAPI.REGISTER, payload)
}
export const login = async (payload: any) => {
    return await http.post(AuthAPI.LOGIN_WITH_PASSWORD, payload)
}
export const forgotPassword = async (payload: { email: string }) => {
    return await http.post(AuthAPI.FORGOT_PASSWORD, payload)
}
export const resetPassword = async (payload: {
    password: string
    passwordRetype: string
    token: string
}) => {
    if (payload.password != payload.passwordRetype)
        throw new Error('Mật khẩu nhập lại không khớp!')
    return await http.post(AuthAPI.RESET_PASSWORD, payload)
}

export const logout = async () => {
    if (!ANONYMOUS_ALLOW_PATHS.includes(location.pathname)) {
        location.href = Paths.LOGIN + '?redirectUrl=' + location.href
    }
    clearAuthToken();
}