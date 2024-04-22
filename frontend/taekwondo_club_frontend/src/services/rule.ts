import http from "@/services/http";
import {AdminApi, AnonymousApi} from "@/constants/API";

export const RuleService = {
    list: async (page: any) => {
        const bodyData = (
            await http.get(AnonymousApi.RULE, {
                params: {
                    page: page
                }
            })
        ).data
        return bodyData;
    },
    add: async (payload: any) => {
        const bodyData = (
            await http.post(AdminApi.RULES, payload)
        ).data
        return bodyData;
    },
    edit: async (id: any, payload: any) => {
        const bodyData = (
            await http.put(AdminApi.RULES + '/' + id, payload)
        ).data
        return bodyData;
    },
    delete: async (id: any) => {
        const bodyData = (
            await http.delete(AdminApi.RULES + '/' + id)
        ).data
        return bodyData;
    },
    getOne: async (id: any) => {
        const bodyData = (
            await http.get(AnonymousApi.RULE + '/' + id)
        ).data
        return bodyData;
    }
}