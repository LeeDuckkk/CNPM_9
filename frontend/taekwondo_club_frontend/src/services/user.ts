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
    },
    add: async (payload: any) => {
        return await http.post(AdminApi.USERS, payload)
    },
    edit: async (id: any, payload: any) => {
        return await http.put(AdminApi.USERS + '/' + id, payload)
    },
    delete: async (id: any) => {
        return await http.delete(AdminApi.USERS + '/' + id)
    },
    getOne: async (id: any) => {
        return await http.get(AdminApi.USERS + '/' + id)
    },
}