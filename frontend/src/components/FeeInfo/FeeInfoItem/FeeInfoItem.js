import React from 'react';
import {useSelector} from "react-redux";
import classes from "../../Community/CommunityInfoItem/item.module.css";
import FeeInfoForm from "../FeeInfoForm/FeeInfoForm";
import {useDeleteFeeInfoMutation} from "../../../store/api/FeeInfoApi";
import Confirm from "../../../UI/confirm/Confirm";


const FeeInfoItem = (props) => {

    const auth = useSelector((state) => state.auth);

    const [isUpdate, setIsUpdate] = React.useState(false);
    const [inputData, setInputData] = React.useState(props.info);

    //更新调用
    // const [updae,updateRes]= useUp
    const [deleteData,deleteDataRes] = useDeleteFeeInfoMutation();
    const [isShowCheckout, setIsShowCheckout] = React.useState(false);

    const[errorMsg, setErrorMsg] = React.useState("");

    //界面变化
    const onChangeHandler = () => {
        setIsUpdate(!isUpdate);
    }
    const deleteHandler = ()=>{
        deleteData(props.info.id).then((res)=>{

            if(deleteDataRes.isError || deleteDataRes.data? deleteDataRes.data.code === 200 : true){
                setErrorMsg(res.data.message);
            }

        })
    }
    const showChekoutHandler=()=>{
        setIsShowCheckout(true);
    }

    const payHandler=()=>{
        setIsShowCheckout(true);
    }
    const cancelHandler=()=>{
        setIsShowCheckout(false);
    }




    return (
        <>
            {isUpdate ||
                <div className={classes.info}>

                    {isShowCheckout &&
                        <Confirm
                            desc={"是否确定进行支付"}
                            yesHandler={payHandler}
                            noHandler={cancelHandler}
                            />
                    }
                    <div className={classes.itemtop}>
                        <span className={classes.IntroducText}>缴费事项：</span>
                        <span className={classes.itemText}>{props.info.extra}</span>

                    </div>
                    {auth.isAdmin &&
                        <div className={classes.items}>
                            <span className={classes.IntroducText}>缴费用户：</span>
                            <span className={classes.itemText}>{props.info.userName}</span>
                        </div>
                    }
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>发布日期：</span>
                        <span className={classes.itemText}>{props.info.date}</span>
                    </div>
                    <div className={classes.items}>
                        <span className={classes.IntroducText}>缴费金额：</span>
                        <span className={classes.itemText}>{props.info.num}</span>
                    </div>
                    <div className={classes.itembuttom}>
                        <span className={classes.IntroducText}>当前状态：</span>
                        <span className={classes.itemText}>{props.info.status ?  '支付完成':'待支付' }</span>
                    </div>
                    <div className={classes.buttons}>
                    { auth.isAdmin  &&
                            <>
                                {!props.info.status &&
                                    <button onClick={onChangeHandler}>修改</button>
                                }

                                <button onClick={deleteHandler}>删除</button>
                            </>
                    }
                    { (!auth.isAdmin && !props.info.status) &&
                        <button onClick={showChekoutHandler}>点此支付</button>
                    }
                    </div>
                </div>
            }
            {isUpdate &&
                <FeeInfoForm info={props.info} cancelShow={onChangeHandler}/>
            }
            {(deleteDataRes.isError || deleteDataRes.data ? deleteDataRes.data.code === 200 : true) ||

                <p>更新失败: {errorMsg}</p>

            }
        </>

    )
        ;
};

export default FeeInfoItem;