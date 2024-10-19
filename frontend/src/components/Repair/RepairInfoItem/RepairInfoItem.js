import {useSelector} from "react-redux";
import React from "react";

import classes from './Repairitem.module.css'
import RepairInfoForm from "../RepairInfoForm/RepairInfoForm";
import {useDeleteCommunityInfoMutation} from "../../../store/api/CommunityApi";
import {useDeleteRepairInfoMutation} from "../../../store/api/RepairInfoApi";


const RepairInfoItem = (props) => {
    const auth = useSelector((state) => state.auth);

    const [isUpdate, setIsUpdate] = React.useState(false);
    const [inputData, setInputData] = React.useState(props.info);
    const[errorMsg, setErrorMsg] = React.useState("");

//更新调用
// const [updae,updateRes]= useUp
    const [deleteData,deleteDataRes] = useDeleteRepairInfoMutation();



//界面变化
    const onChangeHandler = () => {
        setIsUpdate(!isUpdate);
    }
    const deleteHandler = ()=>{
        deleteData(props.info.id).then((res)=>{

            console.log(res);
            if(deleteDataRes.isError || deleteDataRes.data? deleteDataRes.data.code === 200 : true){
                setErrorMsg(res.data.message);
            }

        })
    }


    return (
        <>
            {isUpdate ||
                <div className={classes.info}>
                    {auth.isAdmin &&
                        <div className={classes.itemtop}>
                            <span className={classes.IntroducText}>申请人：</span>
                            <span className={classes.itemText}>{props.info.userName}</span>
                        </div>
                    }
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>报修内容：</span>
                        <span className={classes.itemText}>{props.info.content}</span>
                    </div>
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>当前状态：</span>
                        <span className={classes.itemText}><b>{props.info.status ? "已完成" : "处理中"}</b></span>
                    </div>
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>进度信息：</span>
                        <span className={classes.itemText}>{props.info.extra}</span>
                    </div>
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>提交时间：</span>
                        <span className={classes.itemText}>{props.info.submitTime}</span>
                    </div>
                    <div className={classes.itembuttom}>
                        <span className={classes.IntroducText}>更新时间：</span>
                        <span className={classes.itemText}>{props.info.updateTime}</span>
                    </div>
                        <div className={classes.buttons}>
                            <button onClick={onChangeHandler}>修改</button>
                            {auth.isAdmin &&
                                <button onClick={deleteHandler}>删除</button>
                            }
                        </div>
                </div>
            }
            {isUpdate &&
                <RepairInfoForm info={props.info} cancelShow={onChangeHandler}/>
            }
            {/*{(deleteDataRes.isError || deleteDataRes.data ? deleteDataRes.data.code === 200 : true ) ||*/}

            {/*    <p>更新失败: {errorMsg}</p>*/}

            {/*}*/}
        </>

    )
        ;
};

export default RepairInfoItem;