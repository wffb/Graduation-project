import React from 'react';
import {useAddCommunityInfoMutation, useUpdateCommunityInfoMutation} from "../../../store/api/CommunityApi";
import classes from './ReparInfoForm.module.css';
import {useSelector} from "react-redux";
import {useUpdateAnnounceInfoMutation} from "../../../store/api/AnnounceInfoApi";
import {
    useAddUserRepairInfoMutation,
    useUpdateAdminRepairInfoMutation,
    useUpdateUserRepairInfoMutation
} from "../../../store/api/RepairInfoApi";

const RepairInfoForm = (props) => {

    const auth = useSelector((state) => state.auth);

    //输入数据
    const [inputData, setInputData] = React.useState(props.info);
    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //操作模式
    const [isAdd, setIsAdd] = React.useState((props.info.content?false:true));

    //更新函数
    const [updateUser,updateUserResult]=useUpdateUserRepairInfoMutation();
    const [updateAdmin,updateAdminResult]=useUpdateAdminRepairInfoMutation();

    const[add,addResult]=useAddUserRepairInfoMutation();


    //取消修改返回界面
    const onCancelHandler = ()=>{

        setErrorMsg("");
        setInputData(props.info);
        props.cancelShow();
    }

    const submitHandler = (e)=>{

        console.log(inputData);
        if(isAdd){
            add(inputData).then((res)=>{

                console.log(res);

                if(addResult.isError || addResult.data? addResult.data.code === 200 : true){
                    onCancelHandler();}
                else {
                    setErrorMsg(res.data.message);
                }
            })
        }
        else{
            if(auth.isAdmin){
                updateAdmin(inputData).then(res => {

                    if(updateAdminResult.isError || updateAdminResult.data? updateAdminResult.data.code === 200 : true){
                        onCancelHandler();}
                    else {
                        setErrorMsg(res.data.message);
                    }
                })
            }
            else{
                updateUser(inputData).then(res => {

                    if(updateUserResult.isError || updateUserResult.data? updateUserResult.data.code === 200 : true){
                        onCancelHandler();}
                    else {
                        setErrorMsg(res.data.message);
                    }
                })
            }

        }

    }

    //双向绑定
    const nameChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,name:e.target.value};
        })
    }
    const contentChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,content:e.target.value};
        })
    }
    const statusChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,status:e.target.value};
        })
    }
    const extraChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,extra:e.target.value};
        })
    }

    return (
        <div className={classes.info}>
            {auth.isAdmin ?
                <div className={classes.itemtop}>
                    <span className={classes.IntroducText}>报修账户：</span>
                    <input type="text" value={inputData.userName} onChange={nameChangeHandler}/> :
                </div>
                :
                <div className={classes.itemtop}>
                    <span className={classes.IntroducText}>报修账户：</span>
                    <span className={classes.itemText}>{props.info.userName}</span>
                </div>
            }
            <div className={classes.contentItem}>
                <span className={classes.IntroducText}>报修内容：</span>
                <textarea
                    className={classes.contentText}
                    value={inputData.content}
                    onChange={contentChangeHandler}/>
            </div>
            {auth.isAdmin ?
                <div className={classes.items}>
                    <span className={classes.IntroducText}>当前状态：</span>
                    <select
                        value={inputData.status}
                        onChange={statusChangeHandler}>
                        <option value={false}>处理中</option>
                        <option value={true}>已完成</option>
                    </select>
                </div> :
                <>
                    {isAdd ||
                        <div className={classes.items}>
                            <span className={classes.IntroducText}>当前状态：</span>
                            <span className={classes.itemText}><b>{props.info.status ? "已完成" : "处理中"}</b></span>
                        </div>
                    }
                </>
            }

            {auth.isAdmin ?
                <div className={classes.itembuttom}>
                    <span className={classes.IntroducText}>进度信息：</span>
                    <input type="text" value={inputData.extra} onChange={extraChangeHandler}/> :
                </div> :

                <>
                {isAdd ||
                <div className={classes.items}>
                    <span className={classes.IntroducText}>进度信息：</span>
                    <span className={classes.itemText}>{props.info.extra}</span>
                </div>
                 }
                </>
            }

            <div className={classes.buttons}>
                <button onClick={submitHandler}>提交</button>
                <button onClick={onCancelHandler}>取消</button>

            </div>
            {/*<div className={classes.errorMsg}>*/}
            {/*    {(updateResult.isError || updateResult.data? updateResult.data.code === 200 : true ) ||*/}

            {/*        <p>更新失败: {errorMsg}</p>*/}

            {/*    }*/}
            {/*</div>*/}
        </div>
    );
};

export default RepairInfoForm;