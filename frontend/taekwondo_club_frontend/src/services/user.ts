import http from '@/services/http'
import {AdminApi, AuthAPI, UserApi} from '@/constants/API'


export const UserService = {
    listForAdmin: async (page: any) => {
        const bodyData = (
            await http.get(AdminApi.USER_MANAGE + '/' + page)
        ).data
        return bodyData;
    },
    editProfile: async (payload: any) => {
        return await http.post(UserApi.EDIT_PROFILE, payload)
    },
    changePassword: async (payload: any) => {
        return await http.post(AuthAPI.RESET_PASSWORD, payload)
    }
}