import http from "@/services/http";
import {AdminApi, AnonymousApi} from "@/constants/API";

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
}