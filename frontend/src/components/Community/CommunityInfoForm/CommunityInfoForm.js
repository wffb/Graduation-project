import React, {useState} from 'react';
import classes from "./CommunityInfoForm.module.css"
import {useAddCommunityInfoMutation, useUpdateCommunityInfoMutation} from "../../../store/api/CommunityApi";


const CommunityInfoForm = (props) => {

    //输入数据
    const [inputData, setInputData] = React.useState(props.info);
    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //操作模式
    const [isAdd, setIsAdd] = React.useState((props.info.name?false:true));

    //更新函数
    const [update,updateResult]=useUpdateCommunityInfoMutation();
    const[add,addResult]=useAddCommunityInfoMutation();


    //取消修改返回界面
    const onCancelHandler = ()=>{

        setErrorMsg("");
        setInputData(props.info);
        props.cancelShow();
    }

    const submitHandler = (e)=>{


        if(isAdd){
            add(inputData).then((res)=>{
                
                if(addResult.isError || addResult.data? addResult.data.code === 200 : true){
                    onCancelHandler();}
                else {
                    setErrorMsg(res.data.message);
                }
            })
        }
        else{
            update(inputData).then(res => {

                if(updateResult.isError || updateResult.data? updateResult.data.code === 200 : true){
                    onCancelHandler();}
                else {
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
    const floorsChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,floors:e.target.value};
        })
    }
    const numOfPeopleChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,numOfPeople:e.target.value};
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
                <span className={classes.IntroducText}>建筑名称：</span>
                <input type="text" value={inputData.name} onChange={nameChangeHandler}/> :
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>楼层数：</span>
                <input type="text" value={inputData.floors} onChange={floorsChangeHandler}/> :
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>当前人数：</span>
                <input type="text" value={inputData.numOfPeople} onChange={numOfPeopleChangeHandler}/>
            </div>
            <div className={classes.itembuttom}>
                <span className={classes.IntroducText}>其他信息：</span>
                <input type="text" value={inputData.extra} onChange={extraChangeHandler}/> :
            </div>
            <div className={classes.buttons}>
                <button onClick={submitHandler}>提交</button>
                <button onClick={onCancelHandler}>取消</button>

            </div>
            <div className={classes.errorMsg}>
                {(updateResult.isError || updateResult.data? updateResult.data.code === 200 : true ) ||

                    <p>更新失败: {errorMsg}</p>

                }
            </div>
        </div>
    );
};

export default CommunityInfoForm;