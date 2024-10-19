import {createSlice} from "@reduxjs/toolkit";

const authSlice = createSlice({
        name:'auth',
        initialState: ()=>{

            const token = localStorage.getItem("token");
            //之前未登录
            if(!token){
                return {
                    isLogged:false,
                    isAdmin:false,
                    token:'',
                    user:null,
                    expirationTime:0,
                }
            }
            //之前登录过
            else {
                return {
                    isLogged:true,
                    isAdmin:(localStorage.getItem("isAdmin") === "true"),
                    token:token,
                    user:JSON.parse(localStorage.getItem("user")),
                    expirationTime:+localStorage.getItem("expirationTime"),
                }
            }
        },
        reducers:{
            collectLogin(state, action){

                const now = Date.now();
                //登录有效时间
                const timeout = 1000*60*40;
                // const timeout = 1000*10;

                state.expirationTime = now+timeout;
                state.isLogged = true;
                state.user = action.payload.user;
                state.token = action.payload.token;
                state.isAdmin = action.payload.user.userType;


                localStorage.setItem("user", JSON.stringify(state.user));
                localStorage.setItem("token", state.token);
                localStorage.setItem("expirationTime", state.expirationTime+"");
                localStorage.setItem("isAdmin", state.isAdmin?'true':'false');

            },
            collectLogout(state, action){

                state.isLogged = false;
                state.user = null;
                state.token = '';
                state.isAdmin = false;

                localStorage.removeItem("user");
                localStorage.removeItem("token");
                localStorage.removeItem("expirationTime");
                localStorage.removeItem("isAdmin");
            },
            collectUpdateUserInfo(state, action){

                state.user = action.payload;
                localStorage.removeItem("user");
                localStorage.setItem("user", JSON.stringify(state.user));

            }
        }
})

export const {
    collectLogin,
    collectLogout,
    collectUpdateUserInfo
} = authSlice.actions;
export const {reducer:authReducer}=authSlice;