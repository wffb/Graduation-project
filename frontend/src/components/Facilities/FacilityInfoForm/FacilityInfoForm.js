import React from 'react';
import {
    useAddFacilitiesMutation, useAddFacInfoMutation,
    useUpdateFacilitiesMutation,
    useUpdateFacInfoMutation
} from "../../../store/api/FacilitiesInfoApi";
import {useNavigate} from "react-router-dom";
import {judgeSuccess} from "../../funcTool";
import classes from './FacilityInfoForm.module.css';

const FacilityInfoForm = (props) => {
    //输入数据
    const [inputData, setInputData] = React.useState(props.info);
    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //操作模式
    const [isAdd, setIsAdd] = React.useState((props.info.extra?false:true));

    //更新函数
    const [update,updateResult]=useUpdateFacInfoMutation();
    const[add,addResult]=useAddFacInfoMutation();

    const nav = useNavigate();


    //取消修改返回界面
    const onCancelHandler = ()=>{

        setErrorMsg("");
        setInputData(props.info);
        props.cancelShow();
    }

    const submitHandler = (e)=>{


        if(isAdd){

            add(inputData).then(res => {
                console.log(res);

                if(judgeSuccess(res)){
                    onCancelHandler();
                }
                else {
                    setErrorMsg(res.data.message);
                }
            })
        }
        else{
            update(inputData).then(res => {

                console.log(res);

                if(judgeSuccess(res)){
                    onCancelHandler();
                }
                else {
                    console.log('更新失败');
                    setErrorMsg(res.data.message);
                }
            })
        }

    }

    //双向绑定
    const statusChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,status:e.target.value==='true'?true:false};
        })
    }

    const extraChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,extra:e.target.value};
        })
    }

    return (
        <div className={classes.info}>
            <div className={classes.itemtop}>
                <span className={classes.IntroducText}>维护信息：</span>
                <input type="text" value={inputData.extra} onChange={extraChangeHandler}/>
            </div>
            <div className={classes.itembuttom}>
                <span className={classes.IntroducText}>当前状态：</span>
                <select
                    value={inputData.status}
                    onChange={statusChangeHandler}>
                    <option value={false}>处理中</option>
                    <option value={true}>已完成</option>
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

export default FacilityInfoForm;