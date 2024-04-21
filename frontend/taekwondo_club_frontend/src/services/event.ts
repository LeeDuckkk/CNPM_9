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
    }
}