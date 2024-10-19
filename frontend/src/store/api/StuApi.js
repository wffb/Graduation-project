import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

const StuApi = createApi({

    //Api标识不能和其他api重复
    reducerPath:'stuApi',
    //指定查询的基础信息  发送请求的工具
    baseQuery:fetchBaseQuery({
       //基本url前缀
        baseUrl:"http://192.168.153.129:8080/api",

    }),
    //指定标签用于更新
    tagTypes:['student'],
    //指定api功能，需要一个对象作为返回值,设置请求相关信息
    endpoints(build) {
        return {
            //方法名---类型
                //查询
            getStudents:build.query({
                query() {
                    //请求子路径
                    return {
                        url:'/students',
                        // headers:{
                        //     Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                        // }
                    };
                },
                //调整返回格式
                transformResponse(baseQueryReturnValue, meta, arg) {
                    return baseQueryReturnValue.data;
                },

                providesTags:[{type:'student',id:'LIST'}]
            }),

            getStudentById:build.query({
                query(id) {
                    return `/students/${id}`;
                },
                transformResponse(baseQueryReturnValue, meta, arg) {
                    return baseQueryReturnValue.data;
                },
                //result error 自传参数
                providesTags:(result,error,id)=>{
                    return [{type:'student',id:id,}];
                },
                //缓存过期事件--未使用/组件未挂载时间 s
                keepUnusedDataFor:0
            }),

            deleteStudentById:build.mutation({
                query(id) {
                    return {
                        url:`/students/${id}`,
                        method: "DELETE",
                    }
                }
            }),
            addStudent:build.mutation({
                query(stu) {
                   return {
                       url:'students',
                       method: "POST",
                       body:{
                           data:stu
                       }
                   }
                },
                invalidatesTags:[{type:'student',id:'LIST'}],
            }),
            updateStudent:build.mutation({
                query(stu) {
                    return {
                        url:`students/${stu.id}`,
                        method: "PUT",
                        body:{
                            data:stu.data
                        }
                    }
                },
                invalidatesTags:(result,error,stu)=>
                [
                    {type:'student',id:stu.id},
                    {type:'student',id:'LIST'},
                ],
            })
            // getStudentById:build.query(),
            //     //修改
            // updateStudentById:build.mutation()

        }
    }

})

/**
 * Api创建对象后会根据方法自动生成钩子
 *      查询 funName ---- useFuncNameQuery
 *      修改 funName ---- useFuncNameMutation
 */

export const {
     useGetStudentsQuery
    ,useGetStudentByIdQuery
    ,useDeleteStudentByIdMutation
    ,useAddStudentMutation
    ,useUpdateStudentMutation
} = StuApi;
export default StuApi;
