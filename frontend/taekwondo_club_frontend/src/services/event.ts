import http from "@/services/http";
import {AdminApi, AnonymousApi} from "@/constants/API";

export const EventService = {
    list: async (page: any) => {
        const bodyData = (
            await http.get(AnonymousApi.EVENT, {
                params: {
                    page: page
                }
            })
        ).data
        return bodyData;
    },
    add: async (payload: any) => {
        const bodyData = (
            await http.post(AdminApi.EVENTS, payload)
        ).data
        return bodyData;
    },
    edit: async (id: any, payload: any) => {
        const bodyData = (
            await http.put(AdminApi.EVENTS + '/' + id, payload)
        ).data
        return bodyData;
    },
    delete: async (id: any) => {
        const bodyData = (
            await http.delete(AdminApi.EVENTS + '/' + id)
        ).data
        return bodyData;
    },
    getOne: async (id: any) => {
        const bodyData = (
            await http.get(AdminApi.EVENTS + '/' + id)
        ).data
        return bodyData;
    }
}