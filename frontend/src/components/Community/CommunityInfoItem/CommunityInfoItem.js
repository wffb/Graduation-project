import React from 'react';
import classes from './item.module.css'
import CommunityInfoForm from "../CommunityInfoForm/CommunityInfoForm";
import {useDeleteCommunityInfoMutation} from "../../../store/api/CommunityApi";
import {useSelector} from "react-redux";

const CommunityInfoItem = (props) => {

    const auth = useSelector((state) => state.auth);

    const [isUpdate, setIsUpdate] = React.useState(false);
    const [inputData, setInputData] = React.useState(props.info);

    //更新调用
    // const [updae,updateRes]= useUp
    const [deleteData,deleteDataRes] = useDeleteCommunityInfoMutation();

    const[errorMsg, setErrorMsg] = React.useState("");

    //界面变化
    const onChangeHandler = () => {
        setIsUpdate(!isUpdate);
    }
    const deleteHandler = ()=>{
        deleteData(props.info.name).then((res)=>{

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
                <span className={classes.IntroducText}>建筑名称：</span>
                        <span className={classes.itemText}>{props.info.name}</span>
                </div>
                <div className={classes.items}>
                <span className={classes.IntroducText}>楼层数：</span>
                            <span className={classes.itemText}>{props.info.floors}</span>
                 </div>
                <div className={classes.items}>
                <span className={classes.IntroducText}>当前人数：</span>
                        <span className={classes.itemText}>{props.info.numOfPeople}</span>
                </div>
                <div className={classes.itembuttom}>
                <span className={classes.IntroducText}>其他信息：</span>
                        <span className={classes.itemText}>{props.info.extra}</span>
                </div>
                    {auth.isAdmin &&
                        <div className={classes.buttons}>
                            <button onClick={onChangeHandler}>修改</button>
                            <button onClick={deleteHandler}>删除</button>
                        </div>
                    }
                </div>
            }
            {isUpdate &&
                <CommunityInfoForm info={props.info} cancelShow={onChangeHandler}/>
            }
            {(deleteDataRes.isError || deleteDataRes.data ? deleteDataRes.data.code === 200 : true ) ||

                <p>更新失败: {errorMsg}</p>

            }
        </>

)
    ;
};

export default CommunityInfoItem;