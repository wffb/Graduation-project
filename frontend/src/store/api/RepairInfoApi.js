
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

/**
 * 保修信息
 */
const repairInfoApi = createApi({

    region: 'RepairInfoApi',
    reducerPath: 'RepairInfoApi',

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
            getRepairInfo:build.query({
                query() {
                    return {
                        url:'/getRepair',
                        method:'GET',
                    }
                },
                providesTags:[{type:"RepairInfo",id:"List"}],
            }),

            updateAdminRepairInfo:build.mutation({
                query(info) {
                    return {
                        url:'/admin/updateRepair',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"RepairInfo",id:"List"}];
                },
            }),
            updateUserRepairInfo:build.mutation({
                query(info) {
                    return {
                        url:'/user/updateRepair',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"RepairInfo",id:"List"}];
                },
            }),

            addUserRepairInfo:build.mutation({

                query(info) {
                    return {
                        url:'/user/addRepair',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"RepairInfo",id:"List"}];
                },
            }),
            deleteRepairInfo:build.mutation({

                query(id) {
                    return {
                        url:`/admin/deleteRepair/${id}`,
                        method:'DELETE',
                    }
                },
                invalidatesTags:(result,error)=>{
                    return [{type:"RepairInfo",id:"List"}];
                },
            }),

        }
    }
})

export default repairInfoApi;
export const {
    useGetRepairInfoQuery,
    useUpdateAdminRepairInfoMutation,
    useUpdateUserRepairInfoMutation,
    useDeleteRepairInfoMutation,
    useAddUserRepairInfoMutation,

}=repairInfoApi