import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

/**
 * 公共设施信息
 * 维护信息
 */
const facilitiesInfoApi = createApi({

    region: 'facilitiesInfoApi',
    reducerPath: 'facilitiesInfoApi',

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
            getFacilities:build.query({
                query() {
                    return {
                        url:'/getAllFacilities',
                        method:'GET',
                    }
                },
                providesTags:[{type:"Facilities",id:"List"}],
            }),
            getOneFacility:build.query({
                query(id) {
                    return {
                        url:`/getOneFacility/${id}`,
                        method:'GET',
                    }
                },
                providesTags:(result,error,id)=>{
                    return [{type:"Facilities",id:id}];
                },
            }),

            updateFacilities:build.mutation({
                query(info) {
                    return {
                        url:'/admin/updateFacilities',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"Facilities",id:"List"},{type:"Facilities",id:info.id}];
                },
            }),

            addFacilities:build.mutation({

                query(info) {
                    return {
                        url:'/admin/addFacilities',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"Facilities",id:"List"}];
                },
            }),
            deleteFacilities:build.mutation({

                query(name) {
                    return {
                        url:`/admin/deleteFacilities/${name}`,
                        method:'DELETE',
                    }
                },
                invalidatesTags:(result,error)=>{
                    return [{type:"Facilities",id:"List"}];
                },
            }),
            getFacInfo:build.query({
                query(facId) {
                    return {
                        url:`/user/getFacInfoByFacId/${facId}`,
                        method:'GET',
                    }
                },
                providesTags:(result,error,facId)=>{
                    return [{type:"FacInfo",id:facId}];
                },
            }),
            updateFacInfo:build.mutation({
                query(info) {
                    return {
                        url:'/admin/updateFacInfoByParam',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"FacInfo",id:info.facId}];
                },
            }),
            addFacInfo:build.mutation({

                query(info) {
                    return {
                        url:'/admin/addFacInfoByParam',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"FacInfo",id:info.facId}];
                },
            }),
            /**
             * {id , facId}
             */
            deleteFacInfo:build.mutation({

                query(info) {
                    return {
                        url:`/admin/deleteFacInfoById/${info.id}`,
                        method:'DELETE',
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"FacInfo",id:info.facId}];
                },
            }),

        }
    }
})

export default facilitiesInfoApi;
export const {
    useGetFacilitiesQuery,
    useAddFacilitiesMutation,
    useDeleteFacilitiesMutation,
    useUpdateFacilitiesMutation,
    useGetOneFacilityQuery,

    useGetFacInfoQuery,
    useAddFacInfoMutation,
    useDeleteFacInfoMutation,
    useUpdateFacInfoMutation,

}=facilitiesInfoApi