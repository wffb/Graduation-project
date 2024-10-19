import React from 'react';
import {useAddCommunityInfoMutation, useUpdateCommunityInfoMutation} from "../../../store/api/CommunityApi";
import classes from "../../Community/CommunityInfoForm/CommunityInfoForm.module.css";
import {useAddFeeInfoMutation, useUpdateFeeInfoMutation} from "../../../store/api/FeeInfoApi";
import {judgeSuccess} from "../../funcTool";

const FeeInfoForm = (props) => {
    //输入数据
    const [inputData, setInputData] = React.useState(props.info);
    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //操作模式
    const [isAdd, setIsAdd] = React.useState((props.info.extra?false:true));

    //更新函数
    const [update,updateResult]=useUpdateFeeInfoMutation();
    const[add,addResult]=useAddFeeInfoMutation();


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

                if(judgeSuccess(res)){
                    onCancelHandler();}
                else {
                    setErrorMsg(res.data.message);
                }
            })
        }
        else{
            update(inputData).then(res => {

                if(judgeSuccess(res)){
                    onCancelHandler();
                }
                else {
                    setErrorMsg(res.data.message);
                }
            })
        }

    }

    //双向绑定
    const userNameChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,userName:e.target.value};
        })
    }
    const dateChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,date:e.target.value};
        })
    }
    const numChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,num:e.target.value};
        })
    }
    const extraChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,extra:e.target.value};
        })
    }
    const statusChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,status:e.target.value==='true'?true:false};
        })
    }

    return (
        <div className={classes.info}>
            <div className={classes.itemtop}>
                <span className={classes.IntroducText}>缴费事项：</span>
                <input type="text" value={inputData.extra} onChange={extraChangeHandler}/>
            </div>
            {isAdd ?
                <div className={classes.items}>
                    <span className={classes.IntroducText}>缴费用户：</span>
                    <input
                        type="text"
                        placeholder={"输入'**'则为全体普通用户"}
                        value={inputData.userName}
                        onChange={userNameChangeHandler}/>
                </div>
                :
                <div className={classes.items}>
                <span className={classes.IntroducText}>缴费用户：</span>
                    <span className={classes.itemText}>{props.info.userName}</span>
                </div>
            }
            {isAdd ?
                <div className={classes.items}>
                    <span className={classes.IntroducText}>发布日期：</span>
                    <input
                        placeholder={"请以 yyyy-MM-dd 输入日期"}
                        type="text" value={inputData.date}
                        onChange={dateChangeHandler}/>
                </div>
                :
                <div className={classes.items}>
                    <span className={classes.IntroducText}>发布日期：</span>
                    <span className={classes.itemText}>{props.info.date}</span>
                </div>
            }
            <div className={classes.items}>
            <span className={classes.IntroducText}>缴费金额：</span>
                <input type="text" value={inputData.num} onChange={numChangeHandler}/>
            </div>
            <div className={classes.itembuttom}>
                <span className={classes.IntroducText}>当前状态：</span>
                <select
                    value={inputData.status}
                    onChange={statusChangeHandler}>
                    <option value={false}>待支付</option>
                    <option value={true}>支付完成</option>
                </select>
            </div>
            <div className={classes.buttons}>
                <button onClick={submitHandler}>提交</button>
                <button onClick={onCancelHandler}>取消</button>

            </div>
            {/*<div className={classes.errorMsg}>*/}
            {/*    {(updateResult.isError || updateResult.data ? updateResult.data.code === 200 : true) ||*/}

            {/*        <p>更新失败: {errorMsg}</p>*/}

            {/*    }*/}
            {/*</div>*/}
        </div>
    );
};

export default FeeInfoForm;