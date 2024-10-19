import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

const feeInfoApi = createApi({

    region: ' feeInfoApi',
    reducerPath: ' feeInfoApi',

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
            getFeeInfo:build.query({
                query() {
                    return {
                        url:'/listFeeInfo',
                        method:'GET',
                    }
                },
                providesTags:[{type:"Fee",id:"List"}],

                //时间敏感数据-定时刷新
                keepUnusedDataFor:600
            }),
            getFeeInfoByName:build.query({
                query(name) {
                    return {
                        url:`/listFeeInfoByName/${name}`,
                        method:'GET',
                    }
                },
                providesTags:[{type:"Fee",id:"List"}],
                //时间敏感数据-定时刷新
                keepUnusedDataFor:600
            }),
            updateFeeInfo:build.mutation({
                query(info) {
                    return {
                        url:'/admin/updateFeeInfo',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"Fee",id:"List"}];
                },
            }),
            addFeeInfo:build.mutation({

                query(info) {
                    return {
                        url:'/admin/saveFeeInfo',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"Fee",id:"List"}];
                },
            }),
            deleteFeeInfo:build.mutation({

                query(id) {
                    return {
                        url:`/admin/deleteFeeInfo/${id}`,
                        method:'DELETE',
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"Fee",id:"List"}];
                },
            }),

        }
    }
})

export default feeInfoApi;
export const {
    useGetFeeInfoQuery,
    useGetFeeInfoByNameQuery,
    useUpdateFeeInfoMutation,
    useDeleteFeeInfoMutation,
    useAddFeeInfoMutation,
}=feeInfoApi