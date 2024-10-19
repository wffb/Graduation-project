import React from 'react';
import classes from './Sidebar.module.css'
import SidebarItem from "../SidebarItem/SidebarItem";
import {useSelector} from "react-redux";


const Sidebar = () => {

    const auth = useSelector((state) => state.auth);

    return (
        <div className={classes.sidebar}>
            <SidebarItem to={"/communityInfo"} msg={"社区信息"}/>
            <SidebarItem to={"/announceInfo"}  msg={"公告栏"}/>
            <SidebarItem to={"/repairInfo"}  msg={"报修信息"}/>
            <SidebarItem to={"/facilitiesInfo"} msg={"公共设施"}/>
            <SidebarItem to={"/feeInfo"} msg={"缴费信息"}/>
            {(auth.isLogged && auth.isAdmin) &&
                <SidebarItem to={"/info"} msg={"发布通知"}/>
            }
        </div>
    );
};

export default Sidebar;