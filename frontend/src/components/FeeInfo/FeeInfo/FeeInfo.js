import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";
import {useGetCommunityInfoQuery} from "../../../store/api/CommunityApi";
import classes from "../../Community/CommunityInfo/CommunityInfo.module.css";
import {useGetFeeInfoQuery} from "../../../store/api/FeeInfoApi";
import FeeInfoItem from "../FeeInfoItem/FeeInfoItem";
import FeeInfoForm from "../FeeInfoForm/FeeInfoForm";
import {getReversedList, judgeSuccess} from "../../funcTool";
import FilterContext from "../../../UI/FilterContext/FilterContext";


const FeeInfo = () => {
    const auth = useSelector((state) => state.auth);
    console.log(auth);

    // const [getCommunityInfo,getCommunityInfoRes] = useGetCommunityInfoQuery();
    const res = useGetFeeInfoQuery();
    const [showData,setShowData] = useState([]);
    //确保完成加载
    useEffect(() => {
        if(judgeSuccess(res))
            setShowData(res.data.data);
    }, [res]);

    const [isAdd,setIsAdd] = useState(false);


    const cancelShow = ()=>{
        setIsAdd(!isAdd);
    }

    const changeHandler = (e) => {
        e.preventDefault();
        cancelShow();
    }
    const filterHandler = (kw)=>{

        if(judgeSuccess(res)){
            //不搜索状态
            if(kw === '' || kw === undefined || kw === null)
                setShowData(res.data.data)

            //复制参数
            const copyData = [...res.data.data];
            //筛选备用
            const filterData =copyData.filter((item) => {
                return item.userName.indexOf(kw) !== -1;
            });
            console.log(filterData);

            setShowData(filterData);
        }

    }

    return (
        <div className={classes.cinfo}>
            {auth.isAdmin &&
                <FilterContext
                    msg={"请输入要查询的记录提交用户名"}
                    filterHandler={filterHandler}
                />
            }
            <div className={classes.toptext}>
                <h2>缴费信息</h2>
                {(auth.isAdmin&& !isAdd) &&
                    <a href="#" onClick={changeHandler}>
                        点此添加缴费信息
                    </a>
                }
            </div>
            {isAdd &&
                <FeeInfoForm
                    cancelShow={cancelShow}
                    info={{
                        userName:auth.user.username
                    }}
                />
            }
            {(res.isSuccess&&res.data.code===200) ?
                (
                    getReversedList(showData).map(item=>{
                        return <FeeInfoItem key={item.id} info={item} />
                    })
                ):
                <p>
                    数据加载失败，请刷新重试或重新登录
                </p>
            }
        </div>
    );
};

export default FeeInfo;