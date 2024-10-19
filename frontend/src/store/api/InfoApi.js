import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

const infoApi = createApi({

    region:'infoApi',
    reducerPath: 'infoApi',

    baseQuery:fetchBaseQuery({
        baseUrl: 'http://192.168.153.129:8080/',
        //设置额外请求头
        prepareHeaders:(headers,{getState}) => {
            //获取redux
            const token = getState().auth.token;
            if(token){
                headers.set('Authorization', `${token}`);
            }
            //跨域请求
            headers.set("Access-Control-Allow-Origin", '*');
            return headers;
        }
    }),
    endpoints(build) {
        return {
            setAllInfo:build.mutation({
                query(info) {
                    return {
                        url:`/admin/sendNotice?message=${info.context}`,
                        method:'POST',
                    }
                },
            }),
            setOneInfo:build.mutation({
                query(info) {
                    return {
                        url:`/admin/sendMessage?username=${info.username}&message=${info.context}`,
                        method:'POST',
                    }
                },
            }),

        }
    }
})

export default infoApi;
export const {
    useSetAllInfoMutation,
    useSetOneInfoMutation,
}=infoApi