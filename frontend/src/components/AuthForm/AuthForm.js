import React, {useRef, useState} from 'react';
import {useLoginMutation, useRegisterMutation, useRegisterQuery} from "../../store/api/AuthApi";
import {useDispatch, useSelector} from "react-redux";
import {collectLogin as ReduxLogin,collectLogout as ReduxLogout} from "../../store/reducer/authSlice";
import {useLocation, useNavigate} from "react-router-dom";
import classes from './AuthForm.module.css'
import {Form, InputGroup} from "react-bootstrap";

const AuthForm = () => {
    //获取api
    const [register,registerResult]=useRegisterMutation();
    const[login,loginrResult]=useLoginMutation();

    //获取内部redux
    const dispatch = useDispatch();



    //是否正在登录
    const [isLogin, setIsLogin] = useState(true);
    //操作结构判断
    const [isLoginError,setIsLoginError] = useState(false);

    const usernameInp = useRef();
    const pwdInp = useRef();
    const emailInp=useRef();

    const navigate = useNavigate();

    //获取路径信息
    const location = useLocation();
    //判断吗是否有之前信息
    const from = location.state?.preLocation?.pathname || "/";



    const submitHandler = (e) => {
        //禁止默认提交
        e.preventDefault();
        //获取信息

        const username = usernameInp.current.value;
        const pwd = pwdInp.current.value;


        if(isLogin){

            login({
                username:username,
                password:pwd,
            }).then(res=>{

                //获取结果存入缓存
                if(res.data.code===200){
                    const data = res.data.data;

                    dispatch(ReduxLogin({

                        token:data.token,
                        user:data.user,
                    }));
                    navigate(from,{replace:true});
                }
                else{
                    setIsLoginError(true);
                }
          })


        }else {
            const email = emailInp.current.value;

            register({
                username:username,
                password:pwd,
                email:email,
            });
            navigate(from,{replace:true});
        }



    }

    const auth = useSelector((state) => state.auth);
    console.log(auth);

    return (
        <div className={classes.items}>
            <h2>{isLogin?'登录':'注册'}</h2>

                {registerResult.isError &&
                    <p style={{color: 'red'}}>
                        JSON.stringify(registerResult.error.data.error.message)
                    </p>
                }
                <p style={{color: 'red'}}>
                    {(loginrResult.isError || isLoginError) && "登录失败，账号或密码错误"}
                </p>
                {/*<div className={classes.formItem}>*/}
                {/*    <input ref={usernameInp} type={"text"} placeholder={"用户名"}/>*/}
                {/*</div>*/}
                {/*<div className={classes.formItem}>*/}
                {/*    <input ref={pwdInp} type={"password"} placeholder={"密码"}/>*/}
                {/*</div>*/}
                {/*{!isLogin &&*/}
                {/*    <div >*/}
                {/*        <input ref={emailInp} type={"email"} placeholder={"邮箱"}/>*/}
                {/*    </div>*/}
                {/*}*/}
                <div>

                    <InputGroup  className={classes.formItem}>
                        <InputGroup.Text >用户名</InputGroup.Text>
                        <Form.Control
                            ref={usernameInp}
                            type="text"
                            placeholder="请输入用户名" />
                    </InputGroup>
                    <InputGroup  className={classes.formItem}>
                        <InputGroup.Text >密码 </InputGroup.Text>
                        <Form.Control
                            ref={pwdInp}
                            type="password"
                            placeholder="请输入密码" />
                    </InputGroup>
                    {!isLogin &&
                    <InputGroup  className={classes.formItem}>
                    <InputGroup.Text >邮箱 </InputGroup.Text>
                    <Form.Control
                        ref={emailInp}
                        type="email"
                        placeholder="请输入邮箱" />
                    </InputGroup>
                    }

                </div>

                <div className={[classes.formItem, classes.buttons].join(' ')}>
                    <button onClick={submitHandler}>{isLogin ? '登录' : '注册'}</button>
                    <a href="#" onClick={(e) => {
                        e.preventDefault();
                        setIsLogin(prevState => !prevState)
                    }}>
                        {isLogin ? '还没有账号？速速来注册' : '已有账号？点此登录'}

                    </a>
                </div>
        </div>
    );
};

export default AuthForm;