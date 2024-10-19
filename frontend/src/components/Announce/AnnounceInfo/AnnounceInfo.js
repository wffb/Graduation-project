import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";

import classes from './AnnounceInfo.module.css'
import AnnounceInfoItem from "../AnnounceInfoItem/AnnounceInfoItem";
import {useGetAnnounceInfoQuery} from "../../../store/api/AnnounceInfoApi";
import AnnounceAddForm from "../AnnounceInfoForm/AnnounceAddForm";
import {getReversedList, judgeSuccess} from "../../funcTool";
import FilterContext from "../../../UI/FilterContext/FilterContext";


const AnnounceInfo = () => {
    //引入数据
    const auth = useSelector((state) => state.auth);
    // console.log(auth);

    // const [getCommunityInfo,getCommunityInfoRes] = useGetCommunityInfoQuery();
    const res= useGetAnnounceInfoQuery();
    const [showData,setShowData] = useState([]);
    //确保完成加载
    useEffect(() => {
        if(judgeSuccess(res))
            setShowData(res.data.data);
    }, [res]);
    /**
     * 控制页面变化
     */
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
                return item.title.indexOf(kw) !== -1;
            });
            console.log(filterData);

            setShowData(filterData);
        }
    }


    return (
        <div>

            <FilterContext
                msg={"请输入要查询的公告标题"}
                filterHandler={filterHandler}
            />

            <div className={classes.toptext}>
                <h2>公告信息</h2>
                {(auth.isAdmin && !isAdd )&&
                    <a href="#" onClick={changeHandler}>
                        点此添加公告
                    </a>
                }
            </div>

            {isAdd &&
                <AnnounceAddForm
                    cancelShow={cancelShow}
                />
            }
            {(judgeSuccess(res)) ?
                (
                   getReversedList(showData).map(item => {
                       console.log('开始执行')
                        return <AnnounceInfoItem key={item.id} info={item}/>
                    })
                ):
                <p>
                    数据加载失败，请刷新重试或重新登录
                </p>
            }
        </div>
    );
};



export default AnnounceInfo;