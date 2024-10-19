import {configureStore} from "@reduxjs/toolkit";
import authApi from "./AuthApi";
import {setupListeners} from "@reduxjs/toolkit/query";
import {authReducer} from "../reducer/authSlice";
import stuApi from "./StuApi";
import userInfoApi from "./UserInfoApi";
import communityInfoApi from "./CommunityApi";
import announceInfoApi from "./AnnounceInfoApi";
import repairInfoApi from "./RepairInfoApi";
import facilitiesInfoApi from "./FacilitiesInfoApi";
import feeInfoApi from "./FeeInfoApi";
import {infoReducer} from "../reducer/InfoSlice";
import infoApi from "./InfoApi";

const store = configureStore({

    reducer:{
            [authApi.reducerPath]:authApi.reducer,
            [stuApi.reducerPath]:stuApi.reducer,
            [userInfoApi.reducerPath]:userInfoApi.reducer,
            [communityInfoApi.reducerPath]:communityInfoApi.reducer,
            [announceInfoApi.reducerPath]:announceInfoApi.reducer,
            [repairInfoApi.reducerPath]:repairInfoApi.reducer,
            [facilitiesInfoApi.reducerPath]:facilitiesInfoApi.reducer,
            [feeInfoApi.reducerPath]:feeInfoApi.reducer,
            [infoApi.reducerPath]:infoApi.reducer,
            auth:authReducer,
            info:infoReducer
        },

    middleware:(getDefaultMiddleware) => getDefaultMiddleware()
        .concat(authApi.middleware)
        .concat(stuApi.middleware)
        .concat(userInfoApi.middleware)
        .concat(communityInfoApi.middleware)
        .concat(announceInfoApi.middleware)
        .concat(repairInfoApi.middleware)
        .concat(facilitiesInfoApi.middleware)
        .concat(feeInfoApi.middleware)
        .concat(infoApi.middleware)
});

setupListeners(store.dispatch);

export default store;