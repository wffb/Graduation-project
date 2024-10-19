import React, {useState} from 'react';
import {useParams} from "react-router-dom";
import {useGetFacilitiesQuery, useGetFacInfoQuery, useGetOneFacilityQuery} from "../../../store/api/FacilitiesInfoApi";
import FacilitiesPageItem from "../FacilitiesPageItem/FacilitiesPageItem";
import FacInfoItem from "../FacInfoItem/FacInfoItem";
import {judgeSuccess} from "../../funcTool";
import  classes from './FacilityItem.module.css'
import {useSelector} from "react-redux";
import FacilityInfoForm from "../FacilityInfoForm/FacilityInfoForm";

const FacilityItem = () => {

    const auth = useSelector((state) => state.auth);
    const [isAdd,setIsAdd] = useState(false);

    const {id} = useParams();
    const fac = useGetOneFacilityQuery(id);
    const facInfo = useGetFacInfoQuery(id);

    const cancelShow = ()=>{
        setIsAdd(!isAdd);
    }
    const changeHandler = (e) => {
        e.preventDefault();
        cancelShow();
    }

    return (
        <div className={classes.facItem}>

            { judgeSuccess(fac) ?
                <>
                    <h3>设施信息</h3>
                    <FacilitiesPageItem
                        showBtn={true}
                        info={fac.data}
                    />

                    <div className={classes.midText}>
                    <h3>维护计划</h3>
                    {(auth.isAdmin&& !isAdd) &&
                        <a href="#" onClick={changeHandler}>
                            点此添加维护计划
                        </a>
                    }
                    </div>
                    {(auth.isAdmin && isAdd) &&
                        <FacilityInfoForm info={{
                            facId: +id,
                            status:false
                        }} cancelShow={cancelShow}/>
                    }

                    {judgeSuccess(facInfo) ?
                        facInfo.data.data.map((item, index) => (
                            <FacInfoItem key={item.id} info={item}/>
                        ))
                        :
                        <p>设施维护信息加载失败</p>
                    }
                </>
                :
                <p>数据加载失败</p>
            }


        </div>
    );
};

export default FacilityItem;