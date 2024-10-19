import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

/**
 *社区信息api
 */

const communityInfoApi = createApi({

    region:'commInfoApi',
    regionCode: 'commInfoApi',

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
            getCommunityInfo:build.query({
                query() {
                    return {
                        url:'/getAllCommunity',
                        method:'GET',
                    }
                },
                providesTags:[{type:"Community",id:"List"}],
            }),
            updateCommunityInfo:build.mutation({
                query(info) {
                    return {
                        url:'/admin/updateCommunity',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:[{type:"Community",id:"List"}]
            }),
            addCommunityInfo:build.mutation({

                query(info) {
                    return {
                        url:'/admin/addCommunity',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:[{type:"Community",id:"List"}]
            }),
            deleteCommunityInfo:build.mutation({

                query(info) {
                    return {
                        url:'/admin/deleteCommunity',
                        method:'POST',
                        body:info
                    }
                },
                invalidatesTags:[{type:"Community",id:"List"}]
            }),

        }
    }
})

export default communityInfoApi;
export const {
   useGetCommunityInfoQuery,
   useUpdateCommunityInfoMutation,
    useAddCommunityInfoMutation,
    useDeleteCommunityInfoMutation,
}=communityInfoApi