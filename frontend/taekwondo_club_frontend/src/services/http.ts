import axios from 'axios'
import { LocalStorageKeys } from '@/constants/LocalStorageKey'
import { logout } from '@/services/auth.js'
import { RequestHeaders } from '@/constants/HTTP'
import {getLocalStorage, removeLocalStorage} from "@/helper/LocalStorageHelper";

const http = axios.create({
    withCredentials: true,
    baseURL: import.meta.env.VITE_APP_ROOT_API,
    transformRequest: [
        function (data: any, headers: any) {
            const authToken = getLocalStorage(LocalStorageKeys.AUTHENTICATION_TOKEN);
            if (authToken) {
                headers[RequestHeaders.TOKEN] = 'Bearer ' + authToken;
            }
            return JSON.stringify(data);
        },
    ],
    headers: {
        'Content-Type': 'application/json',
    },
})

http.interceptors.response.use(
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    null,
    (error: any) => {
        if ([401, 403].includes(error.response.status)) {
            // eslint-disable-next-line @typescript-eslint/no-empty-function
            logout().then((r) => {})
        }
        return Promise.reject(error)
    }
)

export const clearAuthToken = () => {
    delete http.defaults.headers.common['Authorization'];
    removeLocalStorage(LocalStorageKeys.AUTHENTICATION_TOKEN);
}

export default http
