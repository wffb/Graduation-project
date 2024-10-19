import React from 'react';
import {useSelector} from "react-redux";
import {NavLink, useNavigate} from "react-router-dom";
import classes from './FacilitiesPageItem.module.css'
import {useDeleteFacilitiesMutation, useUpdateFacilitiesMutation} from "../../../store/api/FacilitiesInfoApi";
import FacilityForm from "../FacilityForm/FacilityForm";
import {judgeSuccess} from "../../funcTool";

const FacilitiesPageItem = (props) => {
    //引入页面缓存
    const auth = useSelector((state) => state.auth);

    const [isUpdate, setIsUpdate] = React.useState(false);
    const [inputData, setInputData] = React.useState(props.info);

    const nav = useNavigate();

    //更新调用
    // const [updae,updateRes]= useUpdateFacilitiesMutation();
    const [deleteData,deleteDataRes] = useDeleteFacilitiesMutation();

    const[errorMsg, setErrorMsg] = React.useState("");

    //界面变化
    const onChangeHandler = () => {
        setIsUpdate(!isUpdate);
    }
    const deleteHandler = ()=>{
        deleteData(props.info.name).then((res)=>{

            console.log(res);

            if(judgeSuccess(res)){
                nav("/facilitiesInfo",{replace:true})
            }
            else
            {
                setErrorMsg(res.data.message);
            }


        })
    }


    return (
        <>
            {isUpdate ||

            <div className={classes.info}>
                <div className={classes.itemtop}>
                    <span className={classes.IntroducText}>设施名称：</span>
                    <span className={classes.itemText}>{props.info.name}</span>
                </div>
                <div className={classes.items}>
                    <span className={classes.IntroducText}>设施类型：</span>
                    <span className={classes.itemText}>{props.info.type}</span>
                </div>
                <div className={classes.items}>
                    <span className={classes.IntroducText}>建立时间：</span>
                    <span className={classes.itemText}>{props.info.setDate}</span>
                </div>
                <div className={classes.items}>
                    <span className={classes.IntroducText}>更新时间：</span>
                    <span className={classes.itemText}>{props.info.updateDate}</span>
                </div>
                <div className={classes.items}>
                    <span className={classes.IntroducText}>待处理事项:</span>
                    <span className={classes.itemText}> <b>{props.info.status}</b> </span>
                </div>
                <div className={classes.itembuttom}>
                    <span className={classes.IntroducText}>备注事项：</span>
                    <span className={classes.itemText}>{props.info.extra}</span>
                </div>
                {(auth.isAdmin && props.showBtn) &&
                    <div className={classes.buttons}>
                        <button onClick={onChangeHandler}>修改</button>
                        <button onClick={deleteHandler}>删除</button>
                    </div>
                }
            </div>
            }
            {isUpdate &&
                <FacilityForm info={props.info} cancelShow={onChangeHandler}/>
            }
            {/*{(deleteDataRes.isError || deleteDataRes.data ? deleteDataRes.data.code === 200 : true ) ||*/}

            {/*    <p>更新失败: {errorMsg}</p>*/}

            {/*}*/}
        </>

    )
};

export default FacilitiesPageItem;