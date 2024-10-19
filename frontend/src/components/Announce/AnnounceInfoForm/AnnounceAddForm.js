import React from 'react';
import {useNavigate} from "react-router-dom";
import {useAddAnnounceInfoMutation} from "../../../store/api/AnnounceInfoApi";
import classes from './AnnounceForm.module.css';
import {judgeSuccess} from "../../funcTool";

const AnnounceAddForm = (props) => {

    //操作模式
    // const isAdd= (props.info.title?false:true)
    // const [isShowUpdate, setIsShowUpdate] = React.useState(false);
    const nav = useNavigate();


    // const{getItem,getItemRes} = useGetAnnounceInfoByIdQuery();
    const[add,addRes]=useAddAnnounceInfoMutation();

    //输入数据
    const [inputData, setInputData] = React.useState({
        title:"",
        content:"",
        extra:""
    });
    // if(!isAdd){
    //
    // }
    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //更新函数
    // const [update,updateResult]=useUpdateCommunityInfoMutation();
    // const[add,addResult]=useAddCommunityInfoMutation();


    //取消修改返回界面
    const onCancelHandler = ()=>{

        setErrorMsg("");
        setInputData({
            title:"",
            content:"",
            extra:""
        });
        props.cancelShow();
    }

    const submitHandler = (e)=>{

        add(inputData).then((res)=>{

            console.log(res);

            if(judgeSuccess(res)){
                onCancelHandler();}
            else {
                setErrorMsg(res.data.message);
            }
        })
    }

    //双向绑定
    const titleChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,title:e.target.value};
        })
    }
    const dateChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,date:e.target.value};
        })
    }
    const contentChangeHandler=(e)=>{
        setInputData((pre)=>{
            return {...pre,content:e.target.value};
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
                <span className={classes.IntroducText}>公告标题：</span>
                <input
                    className={classes.introInput}
                    type="text"
                    value={inputData.title} onChange={titleChangeHandler}/>
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>公告日期：</span>
                <input
                    className={classes.introInput}
                    type="text"
                    value={inputData.date}
                    onChange={dateChangeHandler}/>
            </div>
            <div className={classes.items}>
                <span className={classes.IntroducText}>备注/作者：</span>
                <input
                    className={classes.introInput}
                    type="text"
                    value={inputData.extra}
                    onChange={extraChangeHandler}/>
            </div>
            <div className={classes.itembuttom}>
                <span className={`${classes.IntroducText} `}>公告内容：</span>
                <textarea
                    className={classes.contentText}
                    value={inputData.content}
                    onChange={contentChangeHandler}/>
            </div>

            <div className={classes.buttons}>
                <button onClick={submitHandler}>提交</button>
                <button onClick={onCancelHandler}>取消</button>
            </div>
            {/*<div className={classes.errorMsg}>*/}
            {/*    {(addRes.isError || addRes.data ? addRes.data.code === 200 : true) ||*/}

            {/*        <p>添加失败: {errorMsg}</p>*/}

            {/*    }*/}
            {/*</div>*/}
        </div>
    );
};

export default AnnounceAddForm;