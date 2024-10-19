import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

/**
 * 用于发送请求的用户登录请求的api
 */

const authApi = createApi({

    region: 'authApi',
    reducerPath:'authapi',

    baseQuery:fetchBaseQuery({
        baseUrl: 'http://192.168.153.129:8080/',
        //设置额外请求头
        prepareHeaders:(headers,{getState}) => {

            //获取redux
            const token = getState().auth.token;
            if(token){
                headers.set('Authorization', `${token}`);
            }
            return headers;
        }
    }),
    endpoints(build) {
        return {
            register:build.mutation({
                query(user) {
                    return {
                        url:'register',
                        method:'POST',
                        body:user
                    }
                }
            }),
            login:build.mutation({
                query(user) {
                    return {
                        url:'login',
                        method:'POST',
                        body:user
                    }
                }
            })
        }
    }
})

export default authApi;
export const {
    useRegisterMutation,
    useLoginMutation,
}=authApi