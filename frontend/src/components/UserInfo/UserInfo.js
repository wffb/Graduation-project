import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {useGetUserInfoQuery, useUpdateUserInfoMutation} from "../../store/api/UserInfoApi";
import {collectUpdateUserInfo} from "../../store/reducer/authSlice";

const UserInfo = () => {

    //获取当前缓存的学生值
    const {auth} = useSelector((state) => state);

    //进行界面修改的缓存
    const [inputUserInfo,setInputUserInfo] = useState(auth.user);
    const [isUpdate, setIsUpdate] = useState(false);
    const dispatch = useDispatch();

    let userInfo = auth.user;
    const userInfoSingle = useGetUserInfoQuery(auth.user.username);
    //判断查询结果
    if(userInfoSingle.isSuccess && userInfoSingle.data && userInfoSingle.data.code === 200){
        userInfo = userInfoSingle.data.data;
    }



    const[updateUserInfo,updateUserInfoResult]=useUpdateUserInfoMutation();
    const [isUpdateError,setIsUpdateError] = useState(false);

    const pageChangeHandler = (e)=>{
        e.preventDefault();

        setInputUserInfo(userInfo);
        setIsUpdate(!isUpdate);

    };

    const [pwd,setPwd] = useState("");

    const locationChangeHandler=(e)=>{
        setInputUserInfo((pre)=>{
            return {...pre,location:e.target.value};
        })
    }
    const emailChangeHandler=(e)=>{
        setInputUserInfo((pre)=>{
            return {...pre,email:e.target.value};
        })
    }
    const phoneChangeHandler=(e)=>{
        setInputUserInfo((pre)=>{
            return {...pre,phone:e.target.value};
        })
    }
    const sexChangeHandler = (e)=>{
        setInputUserInfo((pre)=>{
            let res;

            if(e.target.value==='true')
                res=true;
            else
                res=false;

            return {...pre,sex:res};
        })
    }
    const submitHandler = (e)=>{
        const newInfo = {
            ...auth.user,
            ...inputUserInfo,
        };

        updateUserInfo(newInfo).then(res=>{
            //同步更新浏览器缓存
            console.log(res);
            if(res.data.code===200){
                dispatch(collectUpdateUserInfo(newInfo));
                setIsUpdateError(true);
                setIsUpdate(false);
            }
            else{
                setIsUpdateError(true);
            }
            console.log(updateUserInfoResult);
        });
    }


    return (
        <>
            <a href={"#"} onClick={pageChangeHandler}>
                {isUpdate ?
                    "返回用户信息查看界面" :
                    "用户信息不正确?点此修改"
                }
            </a>
            {isUpdate ||
                <ul>
                    <li>用户名: {userInfo.username}</li>
                    <li>性别: {userInfo.sex ? '女' : '男'}</li>
                    <li>当前应缴物业费: <b>{userInfo.fee}</b></li>
                    <li>住址: {userInfo.location ? userInfo.location : '暂无邮箱请尽快完善'}</li>
                    <li>邮箱: {userInfo.email ? userInfo.email : '暂无邮箱请尽快完善'}</li>
                    <li>电话: {userInfo.phone ? userInfo.phone : '暂无联系电话请尽快完善'}</li>
                    <li>账号类型: {userInfo.userType ? '管理员账号' : '一般用户账号'}</li>

                </ul>
            }
            {isUpdate &&
                <div>
                    <ul>
                        <li> 用户名: {inputUserInfo.username} </li>
                        <li>
                            性别：
                            <select
                                onChange={sexChangeHandler}
                                value={inputUserInfo.sex}>
                                <option value={true}>男</option>
                                <option value={false}>女</option>
                            </select>
                        </li>
                        <li>
                            住址: <input type={"text"}
                                         value={inputUserInfo.location}
                                         placeholder={'暂无住址请尽快完善'}
                                         onChange={locationChangeHandler}
                        />
                        </li>
                        <li>
                            邮箱: <input type={"text"}
                                         value={inputUserInfo.email}
                                         placeholder={'暂无邮箱请尽快完善'}
                                         onChange={emailChangeHandler}
                        />
                        </li>
                        <li>
                            电话: <input type={"text"}
                                         value={inputUserInfo.phone}
                                         placeholder={'暂无联系电话请尽快完善'}
                                         onChange={phoneChangeHandler}
                        />
                        </li>
                    </ul>
                    <button onClick={submitHandler}>点此修改</button>
                    {

                    }
                </div>
            }


        </>
    );
};

export default UserInfo;