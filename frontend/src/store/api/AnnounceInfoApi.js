/**
 * 公告Api
 * @returns {Element}
 * @constructor
 */
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

const announceInfoApi = createApi({

    region: 'AnnounceInfoApi',
    reducerPath: 'AnnounceInfoApi',

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
            getAnnounceInfo:build.query({
                query() {
                    return {
                        url:'/getAllAnnounce',
                        method:'GET',
                    }
                },
                providesTags:[{type:"Announce",id:"List"}],
            }),
            getAnnounceInfoById:build.query({
                query(id) {
                    return {
                        url:`/getAnnounce/${id}`,
                        method:'GET',
                    }
                },
                providesTags:(result,error,id)=>{
                    return [{type:'Announce',id:id,}];
                },
                keepUnusedDataFor:0
            }),
            updateAnnounceInfo:build.mutation({
                query(info) {
                    return {
                        url:'/admin/updateAnnounce',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:'Announce',id:info.id,},{type:"Announce",id:"List"}];
                },
            }),
            addAnnounceInfo:build.mutation({

                query(info) {
                    return {
                        url:'/admin/saveAnnounce',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:"Announce",id:"List"}];
                },
            }),
            deleteAnnounceInfo:build.mutation({

                query(info) {
                    return {
                        url:`/admin/deleteAnnounce/${info}`,
                        method:'DELETE',
                    }
                },
                invalidatesTags:(result,error,info)=>{
                    return [{type:'Announce',id:info},{type:"Announce",id:"List"}];
                },
            }),

        }
    }
})

export default announceInfoApi;
export const {
    useGetAnnounceInfoQuery,
    useGetAnnounceInfoByIdQuery,
    useUpdateAnnounceInfoMutation,
    useDeleteAnnounceInfoMutation,
    useAddAnnounceInfoMutation,

}=announceInfoApi