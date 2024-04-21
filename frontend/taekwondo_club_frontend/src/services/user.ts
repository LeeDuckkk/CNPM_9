import http from '@/services/http'
import {AdminApi, AuthAPI} from '@/constants/API'


export const UserService = {
    listForAdmin: async (page: any) => {
        const bodyData = (
            await http.get(AdminApi.USER_MANAGE + '/' + page)
        ).data
        return bodyData.content;
    },
}