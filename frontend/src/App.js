import AppLayout from "./layouts/AppLayout/AppLayout";
import {Link, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage";
import AuthPage from "./pages/AuthPage";
import UserInfoPage from "./pages/UserInfoPage";
import UserInfo from "./components/UserInfo/UserInfo";
import CommunityInfo from "./components/Community/CommunityInfo/CommunityInfo";
import CommunityInfoPage from "./pages/CommunityInfoPage";
import AnnounceInfoPage from "./pages/AnnounceInfoPage";
import AnnounceInfoUpdateForm from "./components/Announce/AnnounceInfoForm/AnnounceInfoUpdateForm";
import RepairInfoPage from "./pages/RepairInfoPage";
import RepairInfo from "./components/Repair/RepairInfo/RepairInfo";
import NeedAuth from "./components/NeedAuth/NeedAuth";
import Facilities from "./components/Facilities/Facilities/Facilities";
import FacilityItem from "./components/Facilities/FacilityItem/FacilityItem";
import FacilitiesPage from "./pages/FacilitiesPage";
import FeeInfo from "./components/FeeInfo/FeeInfo/FeeInfo";
import FeeInfoPage from "./pages/FeeInfoPage";
//boostrap 引入样式
import 'bootstrap/dist/css/bootstrap.min.css';
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {collectLogout as logout} from "./store/reducer/authSlice";
import Info from "./components/WebSocket/Info";
import InfoForm from "./components/InfoForm/InfoForm";
import InfoPage from "./pages/InfoPage";


function App() {

    //获取全局信息
    const auth = useSelector((state) => state.auth);
    const dispatch = useDispatch();

  //总体设置失效时间
    useEffect(() => {

        //未登录不判断
        if(!auth.isLogged)
            return ;

        const timeout = auth.expirationTime-Date.now();

        if(timeout<1000*60){
            dispatch(logout)
            return
        }

        const timer= setTimeout(()=>{
            dispatch(logout)
        },timeout);


        //清除过期定时器
        return ()=>{
            clearTimeout(timer)
        }
    }, [auth, dispatch]);


  return (
    <div >
        <AppLayout>
            {auth.isLogged &&
                <Info/>
            }

            <Routes>
                <Route path={"/home"} element={<HomePage/>} />
                <Route path={"/"} element={<HomePage/>} />

                {/*<Route path="/profile" element={*/}
                {/*    <NeedAuth>*/}
                {/*        <Profile/>*/}
                {/*    </NeedAuth>*/}
                {/*}/>*/}
                <Route path="/authForm" element={<AuthPage/>} />
                <Route path={"/userInfo"} element={<UserInfoPage/>} />
                <Route path="/communityInfo" element={<CommunityInfoPage/>} />
                <Route path="/announceInfo" element={<AnnounceInfoPage/>} />
                <Route path="/announceInfo/:id" element={<AnnounceInfoUpdateForm/>} />
                <Route path="/facilitiesInfo" element={
                    <NeedAuth>
                        <FacilitiesPage/>
                    </NeedAuth>
                    }/>
                <Route path="/facilitiesInfo/:id" element={
                    <NeedAuth>
                        <FacilityItem />
                    </NeedAuth>
                } />
                <Route path="/repairInfo" element={
                    <NeedAuth>
                        <RepairInfoPage/>
                    </NeedAuth>
                }/>
                <Route path="/feeInfo" element={
                    <NeedAuth>
                        <FeeInfoPage/>
                    </NeedAuth>
                }/>
                <Route path="/info" element={
                    <NeedAuth>
                        <InfoPage/>
                    </NeedAuth>
                }/>
            </Routes>
        </AppLayout>
    </div>
  );
}

export default App;
