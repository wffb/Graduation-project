import React from 'react';
import {useAddCommunityInfoMutation, useUpdateCommunityInfoMutation} from "../../../store/api/CommunityApi";
import classes from './FacilityForm.module.css'
import {judgeSuccess} from "../../funcTool";
import {useAddFacilitiesMutation, useUpdateFacilitiesMutation} from "../../../store/api/FacilitiesInfoApi";
import {useNavigate} from "react-router-dom";

const FacilityForm = (props) => {

    //输入数据
    const [inputData, setInputData] = React.useState(props.info);
    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //操作模式
    const [isAdd, setIsAdd] = React.useState((props.info.name?false:true));

    //更新函数
    const [update,updateResult]=useUpdateFacilitiesMutation();
    const[add,addResult]=useAddFacilitiesMutation();

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
    const nameChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,name:e.target.value};
        })
    }
    const typeChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,type:e.target.value};
        })
    }
    const setDateChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,setDate:e.target.value};
        })
    }
    const updateDateChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,updateDate:e.target.value};
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
                <span className={classes.IntroducText}>设施名称：</span>
                <input type="text" value={inputData.name} onChange={nameChangeHandler}/> :
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>设施类型：</span>
                <input type="text" value={inputData.type} onChange={typeChangeHandler}/> :
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>建立时间：</span>
                <input type="text"
                       placeholder={"请以 yyyy-MM-dd 输入日期"}
                       value={inputData.setDate}
                       onChange={setDateChangeHandler}/>
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>更新时间：</span>
                <input type="text"
                       placeholder={"请以 yyyy-MM-dd 输入日期"}
                       value={inputData.updateDate}
                       onChange={updateDateChangeHandler}/> :
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>待处理事项:</span>
                <span className={classes.itemText}><b>{props.info.status}</b> </span>
            </div>
            <div className={classes.itembuttom}>
                <span className={classes.IntroducText}>备注事项：</span>
                <input type="text" value={inputData.extra} onChange={extraChangeHandler}/> :
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

export default FacilityForm;