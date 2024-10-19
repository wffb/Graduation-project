import React from 'react';
import classes from './AppLayout.module.css'
import Menu from "../Menu/Menu";
import StuApi from "../../store/api/StuApi";
import Sidebar from "../Sidebar/Sidebar";
import BMenu from "../BMenu/BMenu";

const AppLayout = (props) => {
    return (
        <div className={classes.index}>
            {/*<Menu/>*/}
            <BMenu/>
            <hr/>
            <div className={classes.items}>
            {props.children}
            </div>
            <Sidebar/>
        </div>
    );
};

export default AppLayout;