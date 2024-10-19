import React, {useEffect} from 'react';
import classes from './AnnounceForm.module.css';
import {useNavigate, useParams} from "react-router-dom";
import {
    useDeleteAnnounceInfoMutation,
    useGetAnnounceInfoByIdQuery,
    useUpdateAnnounceInfoMutation
} from "../../../store/api/AnnounceInfoApi";
import {useSelector} from "react-redux";
import {judgeSuccess} from "../../funcTool";


const AnnounceInfoUpdateForm = (props) => {
    //载入数据
    const auth = useSelector((state) => state.auth);

    //操作模式
    /**
     * 是否为添加
     *      添加：不能进行切换
     *      更新：可以进行展示切换
     */
    const [isShowUpdate, setIsShowUpdate] = React.useState(false);


    //获取函数
    const nav = useNavigate();
    const [update,updateRes]=useUpdateAnnounceInfoMutation();
    const [deleteInfo,deleteRes]=useDeleteAnnounceInfoMutation();
    let delet

    //获取传入路由参数
    const {id} = useParams();
    // console.log(id);


    //输入数据
    const getRes = useGetAnnounceInfoByIdQuery(id);
    const [inputData, setInputData] = React.useState(getRes.data);
    console.log(getRes);

    useEffect(() => {
        setInputData(getRes.data);
    }, [getRes]);


    //错误信息
    const[errorMsg, setErrorMsg] = React.useState("");
    //更新函数
    // const [update,updateResult]=useUpdateCommunityInfoMutation();
    // const[add,addResult]=useAddCommunityInfoMutation();


    //取消修改返回界面
    const onCancelHandler = ()=>{

        setErrorMsg("");
        setInputData(getRes.data);
        setIsShowUpdate(false);
    }
    const showHandler=()=>{
        setIsShowUpdate(true);
    }

    const submitHandler = (e)=>{

            update(inputData).then(res => {


                if(judgeSuccess(res)){
                    onCancelHandler();
                }
                else {
                    setErrorMsg(res.data.message);
                }
            })
        }


    //删除处理器
    const onDeleteHandler = ()=>{
        deleteInfo(id).then(res => {

            console.log(id);
            console.log(res);

            if(res.error || deleteRes.isError ||  res.data.code?  res.data.code !== 200 : true) {

                setErrorMsg(res.data.message);
            }
            else {
                nav("/announceInfo",{replace:true});
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
        <>
        { !deleteRes.isSuccess ?
                <div className={classes.info}>
                    {(getRes.isSuccess && (getRes.data? (getRes.data.code? getRes.data.code===200 :true) : false)) ?
                        <>
                            {(isShowUpdate) &&
                                <>
                                    <div className={classes.itemtop}>
                                        <span className={classes.IntroducText}>公告标题：</span>
                                        <input type="text" value={inputData.title} onChange={titleChangeHandler}/>
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
                                    <div className={classes.errorMsg}>
                                        {(updateRes.isError || updateRes.data ? updateRes.data.code === 200 : true) ||

                                            <p>更新失败: {errorMsg}</p>

                                        }
                                    </div>
                                </>

                            }
                            {(!isShowUpdate) &&
                                <>
                                    <div className={classes.itemtop}>
                                        <span className={classes.IntroducText}>公告标题：</span>
                                        <span className={classes.itemText}>{getRes.data.title}</span>
                                    </div>
                                    <div className={classes.items}>
                                        <span className={classes.IntroducText}>公告日期：</span>
                                        <span className={classes.itemText}>{getRes.data.date}</span>
                                    </div>
                                    <div className={classes.items}>
                                        <span className={classes.IntroducText}>备注/作者：</span>
                                        <span className={classes.itemText}>{getRes.data.extra}</span>
                                    </div>
                                    <div className={classes.itembuttom}>
                                        <span className={classes.IntroducText}>公告内容：</span>
                                        <span className={classes.itemText}>{getRes.data.content}</span>
                                    </div>
                                    {auth.isAdmin &&
                                        <div className={classes.buttons}>
                                            <button onClick={showHandler}>修改</button>
                                            <button onClick={onDeleteHandler}>删除</button>
                                        </div>
                                    }
                                    <div className={classes.errorMsg}>
                                        {(deleteRes.isError || deleteRes.data ? deleteRes.data.code === 200 : true) ||

                                            <p>删除失败: {errorMsg}</p>

                                        }
                                    </div>
                                </>
                            }
                        </> :
                        <>
                            <p>数据加载失败{getRes.data ? getRes.data.message : ''}，可以刷新或重新登录后重试</p>
                        </>
                    }
                    {getRes.isLoading &&
                        <p>数据加载中请稍后</p>
                    }

                </div>:
                <p>该公告已被删除</p>
        }
        </>
    );
};

export default AnnounceInfoUpdateForm;