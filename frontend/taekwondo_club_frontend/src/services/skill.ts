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
    }
}