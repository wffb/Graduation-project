import React from 'react';
import {useSelector} from "react-redux";
import classes from './AnnounceInfoItem.module.css';
import CommunityInfoForm from "../../Community/CommunityInfoForm/CommunityInfoForm";
import {Link, NavLink} from "react-router-dom";


const AnnounceInfoItem = (props) => {

    //引入页面缓存
    const auth = useSelector((state) => state.auth);

    const [isUpdate, setIsUpdate] = React.useState(false);
    const [inputData, setInputData] = React.useState(props.info);

    //更新调用
    // const [updae,updateRes]= useUp
    // const [deleteData,deleteDataRes] = useDeleteCommunityInfoMutation();

    const[errorMsg, setErrorMsg] = React.useState("");

    //界面变化
    const onChangeHandler = () => {
        setIsUpdate(!isUpdate);
    }
    const deleteHandler = ()=>{
        // deleteData(props.info.name).then((res)=>{
        //
        //     if(deleteDataRes.isError || deleteDataRes.data? deleteDataRes.data.code === 200 : true){
        //         setErrorMsg(res.data.message);
        //     }
        //
        // })
    }

    console.log(props.info);

    return (
        <>
            {isUpdate ||
                <NavLink className={classes.link} to={`/announceInfo/${props.info.id}`}>
                <div className={classes.info}>
                    <div className={classes.itemtop}>
                        <span className={classes.IntroducText}>公告标题：</span>
                        <span className={classes.itemText}>{props.info.title}</span>
                    </div>
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>公告日期：</span>
                        <span className={classes.itemText}>{props.info.date}</span>
                    </div>
                    <div className={classes.itembuttom}>
                        <span className={classes.IntroducText}>备注/作者：</span>
                        <span className={classes.itemText}>{props.info.extra}</span>
                    </div>
                    {/*{auth.isAdmin &&*/}
                    {/*    <div className={classes.buttons}>*/}
                    {/*        <button onClick={onChangeHandler}>修改</button>*/}
                    {/*        <button onClick={deleteHandler}>删除</button>*/}
                    {/*    </div>*/}
                    {/*}*/}
                </div>
                </NavLink>
            }
            {/*{isUpdate &&*/}
            {/*    <CommunityInfoForm info={props.info} cancelShow={onChangeHandler}/>*/}
            {/*}*/}
            {/*{(deleteDataRes.isError || deleteDataRes.data ? deleteDataRes.data.code === 200 : true ) ||*/}

            {/*    <p>更新失败: {errorMsg}</p>*/}

            {/*}*/}
        </>

    )
};

export default AnnounceInfoItem;