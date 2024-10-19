/**
 * 工具方法
 */

export const judgeSuccess = (res)=>{

   return  !res.error  && !res.isError && (res.isSuccess!==undefined?res.isSuccess:true) && (res.data.code?res.data.code===200:true);
}

export const getReversedList = (list)=>{


   const copyList = [...list]
   copyList.reverse();

   console.log(copyList);

   return copyList;
}

