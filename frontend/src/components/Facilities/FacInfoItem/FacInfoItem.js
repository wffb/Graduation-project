import React from 'react';
import {useSelector} from "react-redux";
import classes from './FacInfoItem.module.css';
import FacilityInfoForm from "../FacilityInfoForm/FacilityInfoForm";
import {useDeleteFacInfoMutation} from "../../../store/api/FacilitiesInfoApi";

const FacInfoItem = (props) => {


    //引入页面缓存
    const auth = useSelector((state) => state.auth);

    const [isUpdate, setIsUpdate] = React.useState(false);
    const [inputData, setInputData] = React.useState(props.info);

    //更新调用
    // const [updae,updateRes]= useUp
    const [deleteData,deleteDataRes] = useDeleteFacInfoMutation();

    const[errorMsg, setErrorMsg] = React.useState("");

    //界面变化
    const onChangeHandler = () => {
        setIsUpdate(!isUpdate);
    }
    const deleteHandler = ()=>{
        deleteData({
            id: props.info.id,
            facId: props.info.facId,
        }).then((res)=>{

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
                <div className={classes.itemtop}>
                    <span className={classes.IntroducText}>维护信息：</span>
                    <span className={classes.itemText}>{props.info.extra}</span>
                </div>
                <div className={classes.items}>
                    <span className={classes.IntroducText}>当前状态：</span>
                    <span className={classes.itemText}> <b>{props.info.status ? '已完成' : '处理中'}</b></span>
                </div>
                <div className={classes.items}>
                    <span className={classes.IntroducText}>发起日期：</span>
                    <span className={classes.itemText}>{props.info.setTime}</span>
                </div>
                <div className={classes.itembuttom}>
                    <span className={classes.IntroducText}>修改日期：</span>
                    <span className={classes.itemText}>{props.info.updateTime}</span>
                </div>

                {auth.isAdmin &&
                    <div className={classes.buttons}>
                        <button onClick={onChangeHandler}>修改</button>
                        <button onClick={deleteHandler}>删除</button>
                    </div>
                }
                {(deleteDataRes.isError || deleteDataRes.data ? deleteDataRes.data.code === 200 : true ) ||

                    <p className={classes.errorMsg}>更新失败: {errorMsg}</p>

                }
            </div>
             }
             {isUpdate &&
                < FacilityInfoForm info={props.info} cancelShow={onChangeHandler}/>
             }

         </>

    )
};

export default FacInfoItem;