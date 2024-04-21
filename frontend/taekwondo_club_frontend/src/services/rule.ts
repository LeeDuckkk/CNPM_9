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
    }
}