/**
 * 查看用户信息Api
 * @returns {Element}
 * @constructor
 */
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

const userInfoApi = createApi({

    region: 'userInfoApi',
    reducerPath: 'userInfoApi',

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
            updateUserInfo:build.mutation({
                query(userInfo) {
                    return {
                        url:'/updateUserInfo',
                        method:'POST',
                        body:userInfo
                    }
                }
            }),
            getUserInfo:build.query({
                query(userInfo) {
                    return {
                        url:`/getUserInfoByUsername/${userInfo}`,
                        method:'GET',
                    }
                }
            }),

        }
    }
})

export default userInfoApi;
export const {
   useUpdateUserInfoMutation,
   useGetUserInfoQuery

}=userInfoApi