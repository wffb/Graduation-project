import React from 'react';
import {Button, Container, Stack} from "react-bootstrap";
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import {Link, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {collectLogout as logout} from "../../store/reducer/authSlice";
import {clearInfo} from "../../store/reducer/InfoSlice";

const BMenu = () => {

    const {auth} = useSelector(state => state);
    const navigate = useNavigate();

    const dispatch = useDispatch();
    const logoutHandler = ()=>{

        dispatch(logout());
        dispatch(clearInfo());
        navigate('/');
    }

    return (
        <Navbar
            expand="sm"
            variant="light"
            className="bg-body-tertiary"
            defaultValue={"/home"}
            // sticky="top"
        >
            <Container>
                <Navbar.Brand >小区管理系统</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />

                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/home">主页</Nav.Link>
                        {auth.isLogged &&
                            <Nav.Link href={"/"} onClick={logoutHandler}>登出</Nav.Link>
                        }
                        {auth.isLogged &&
                            <Nav.Link  href={"/userInfo"}> 用户信息</Nav.Link>

                        }
                        {auth.isLogged ||
                            <Nav.Link href="/authForm">登录/注册</Nav.Link>

                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>

    );
};

export default BMenu;