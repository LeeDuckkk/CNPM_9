import http from "@/services/http";
import {AdminApi, AnonymousApi} from "@/constants/API";

export const SkillService = {
    list: async (page: any) => {
        const bodyData = (
            await http.get(AnonymousApi.SKILL, {
                params: {
                    page: page
                }
            })
        ).data
        return bodyData;
    },
    add: async (payload: any) => {
        return await http.post(AdminApi.SKILLS, payload)
    },
    edit: async (id: any, payload: any) => {
        return await http.put(AdminApi.SKILLS + '/' + id, payload)
    },
    delete: async (id: any) => {
        return await http.delete(AdminApi.SKILLS + '/' + id)
    },
    getOne: async (id: any) => {
        return await http.get(AdminApi.SKILLS + '/' + id)
    }
}