import React from 'react';
import {Link, NavLink, useNavigate} from "react-router-dom";
import classes from './Menu.module.css'
import {useDispatch, useSelector} from "react-redux";
import {collectLogout as logout} from "../../store/reducer/authSlice";


const Menu = () => {

    const {auth} = useSelector(state => state);
    const navigate = useNavigate();

    const dispatch = useDispatch();
    const logoutHandler = ()=>{

        dispatch(logout());
        navigate('/');
    }

    return (
        <div className={classes.menu}>
            <ul>
                <li>
                    <NavLink style={({isActive})=>{
                        return  isActive?{color:"red"}:null;
                    }} to="/">首页</NavLink>
                </li>
                {auth.isLogged &&
                    <li>
                        <Link to="/" onClick={logoutHandler}>登出</Link>
                    </li>
                }
                {auth.isLogged &&
                    <li>
                        <Link to="/userInfo" >用户信息</Link>
                    </li>
                }
                {auth.isLogged ||
                    <li>
                    <Link to="/authForm">登录/注册</Link>
                    </li>

                }

            </ul>
        </div>
    );
};

export default Menu;