import React, {useState} from 'react';
import {useGetCommunityInfoQuery} from "../../../store/api/CommunityApi";
import CommunityInfoItem from "../CommunityInfoItem/CommunityInfoItem";
import classes from './CommunityInfo.module.css'
import CommunityInfoForm from "../CommunityInfoForm/CommunityInfoForm";
import {useSelector} from "react-redux";

const CommunityInfo = () => {

    const auth = useSelector((state) => state.auth);
    console.log(auth);

    // const [getCommunityInfo,getCommunityInfoRes] = useGetCommunityInfoQuery();
    const res = useGetCommunityInfoQuery();

    const [isAdd,setIsAdd] = useState(false);


    const cancelShow = ()=>{
        setIsAdd(!isAdd);
    }

    const changeHandler = (e) => {
       e.preventDefault();
       cancelShow();
    }


    return (
        <div className={classes.cinfo}>
            <div className={classes.toptext}>
            <h2>社区信息</h2>
                {(auth.isAdmin&& !isAdd) &&
                    <a href="#" onClick={changeHandler}>
                        点此添加社区信息
                    </a>
                }
            </div>
            {isAdd &&
                <CommunityInfoForm
                    cancelShow={cancelShow}
                    info={{}}
                />
            }
            {(res.isSuccess&&res.data.code===200) ?
                (
                  res.data.data.map(item=>{
                      return <CommunityInfoItem key={item.id} info={item} />
                  })
                ):
                <p>
                    数据加载失败，请刷新重试或重新登录
                </p>
            }
        </div>
    );
};

export default CommunityInfo;