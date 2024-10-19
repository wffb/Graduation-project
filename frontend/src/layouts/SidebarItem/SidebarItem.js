import React from 'react';
import classes from './SidebarItem.module.css';
import {NavLink} from "react-router-dom";

const SidebarItem = (props) => {
    return (
        <div className={classes.sidebarItem}>
            <NavLink
                to={props.to}
                className={({isActive})=>{
                   return `${classes.link} ${isActive?classes.active:''}`
                }}
            >{props.msg}</NavLink>
        </div>
    );
};

export default SidebarItem;