import React, {useState} from 'react';
import {useSelector} from "react-redux";
import classes from './Facilities.module.css'
import {useGetFacilitiesQuery} from "../../../store/api/FacilitiesInfoApi";
import FacilitiesPageItem from "../FacilitiesPageItem/FacilitiesPageItem";
import {NavLink} from "react-router-dom";
import FacilityForm from "../FacilityForm/FacilityForm";

const Facilities = () => {

    //引入数据
    const auth = useSelector((state) => state.auth);
    // console.log(auth);

    // const [getCommunityInfo,getCommunityInfoRes] = useGetCommunityInfoQuery();
    const res = useGetFacilitiesQuery();




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


    return (
        <div className={classes.info}>
            <div className={classes.toptext}>
                <h2>公共设施</h2>
                {(auth.isAdmin && !isAdd )&&
                    <a href="#" onClick={changeHandler} className={classes.headLink}>
                        点此添加设施信息
                    </a>
                }
            </div>
            {isAdd &&
                <FacilityForm
                    info={{
                        status:0
                    }}
                    cancelShow={cancelShow}
                />
            }
            {(res.isSuccess&&res.data.code===200) ?
                (
                    res.data.data.map(item=>{
                        return (
                            <NavLink key={item.id} className={classes.link} to={`/facilitiesInfo/${item.id}`}>
                                <FacilitiesPageItem  info={item} />
                            </NavLink>
                                )
                    })
                ):
                <p>
                    数据加载失败，请刷新重试或重新登录
                </p>
            }
        </div>
    );
};

export default Facilities;