import {createSlice} from "@reduxjs/toolkit";

const infoSlice = createSlice({
    name:'info',
    initialState: ()=>{

        const context = localStorage.getItem("context");
        //之前未登录
        if(!context){
            return {
                context:null,
                contextType:null,
            }
        }
        //之前登录过
        else {
            return {
                context:localStorage.getItem("context"),
                contextType:localStorage.getItem("contextType"),
            }
        }
    },
    reducers:{

        setInfo(state, action){

            state.context = action.payload.context;
            state.contextType = action.payload.contextType;

            localStorage.setItem("context",  state.context+"");
            localStorage.setItem("contextType", state.contextType?'true':'false');

        },
        clearInfo(state, action){

            state.context = null;
            state.contextType = null;

            localStorage.removeItem("context");
            localStorage.removeItem("contextType");
        },

    }
})

export const {
    setInfo,
   clearInfo,
} =infoSlice.actions;
export const {reducer:infoReducer}=infoSlice;