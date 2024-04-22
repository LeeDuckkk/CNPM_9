import http from "@/services/http";
import {AdminApi, AnonymousApi} from "@/constants/API";
import axios from "axios";
import {getLocalStorage} from "@/helper/LocalStorageHelper";
import {LocalStorageKeys} from "@/constants/LocalStorageKey";
import {RequestHeaders} from "@/constants/HTTP";

const axiosInstance = axios.create({
    withCredentials: true,
    baseURL: import.meta.env.VITE_APP_ROOT_API,
    headers: {
        'Content-Type': 'multipart/form-data',
    },
});


export const ConfessionService = {
    list: async (page: number = 0) => {
        const response = await http.get(AnonymousApi.CONFESSION, {
            params: {
                page: page,
            }
        });
        return response.data;
    },
    getById: async (id: any) => {
        const bodyData = (
            await http.get(AnonymousApi.CONFESSION + `/${id}`)
        ).data
        return bodyData;
    },
    getComments: async (id: any, page: number = 0) => {
        const bodyData = await http.get(AnonymousApi.CONFESSION + `/${id}/comments`, {
                params: {
                    page: page
                }
            });
        return bodyData.data;
    },
    addComment: async (id: any, content: any) => {
        const bodyData = await http.post(AnonymousApi.CONFESSION + `/${id}/comment`, {
            content: content
        });
        return bodyData.data;
    },
    add: async (payload: any) => {
        console.log(payload)
        return (
            await axiosInstance.post(AdminApi.CONFESSIONS, payload)
        ).data;
    },
    edit: async (id: any, payload: any) => {
        const bodyData = (
            await http.put(AdminApi.CONFESSIONS + '/' + id, payload)
        ).data
        return bodyData;
    },
    delete: async (id: any) => {
        const bodyData = (
            await http.delete(AdminApi.CONFESSIONS + '/' + id)
        ).data
        return bodyData;
    },
    all_confession: async (page: any) => {
        const bodyData = (
            await http.get(AdminApi.UNAPPROVED_CONFESSIONS, {
                params: {
                    page: page
                }
            })
        ).data
        return bodyData;
    }
}