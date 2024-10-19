import React, {useEffect} from 'react';
import classes from './FilterContext.module.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSearch} from "@fortawesome/free-solid-svg-icons";
const FilterContext = (props) => {

    const[kw, setkw] = React.useState('');

    const inputChangeHandler = (e) => {
        setkw(e.target.value.trim())
        // props.filterHandler(key);
    }

    useEffect(() => {

        //避免在用户输入过程中进行过滤，设置1s延迟

        //设置本次计时器
        const timer= setTimeout(
            ()=>{
                props.filterHandler(kw);
            },
            300)

        //Effect的参数函数可以返回一个函数，这个函数将在下个Effect执行前调用
        //清理上次计时器
        return ()=>{
            clearTimeout(timer);
        }

    }, [kw]);

    return (
        <div className={classes.FilterMeals}>
            <input
                value={kw}
                type={"text"}
                placeholder={props.msg}
                className={classes.searchInput}
                onChange={inputChangeHandler}
            />
            <FontAwesomeIcon icon={faSearch}  className={classes.searchIcon}/>
        </div>
    );
};

export default FilterContext;