import React, {useContext} from 'react';
import './counter.module.css'
import classes from './counter.module.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from '@fortawesome/free-solid-svg-icons';
import CartContext from "../../store/CartContext";


/**
 *
    引入FontAwesome
        -安装依赖
    npm i --save @fortawesome/fontawesome-svg-core

    npm i --save @fortawesome/free-solid-svg-icons
    npm i --save @fortawesome/free-regular-svg-icons

    npm i --save @fortawesome/react-fontawesome@latest
        -引入组件
    import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
        -引入图标
    import {faPlus, faTimes} from '@fortawesome/free-solid-svg-icons';
        -使用图标
    <FontAwesomeIcon icon={faPlus} />
 */

const Counter = (props) => {


    const cartContext = useContext(CartContext);

    const onAddButton=()=>{
        cartContext.cartDispatch({type:'ADD',meal:props.meal});

    }

    const onDeclineButton=()=>{
        cartContext.cartDispatch({type:'DELETE',meal:props.meal});
    }


    return (
            <div className={classes.counterWrapper}>
                {
                    // 利用表达式控制显示
                    (props.meal.amount && (
                    <>
                    <button
                        className={classes.decline}
                        onClick={onDeclineButton}
                    >-</button>
                    <span>{props.meal.amount}</span>
                    </>
                        ) ||
                   null)
                }

            <button className={classes.add}>
                <FontAwesomeIcon
                    icon={faPlus}
                    onClick={onAddButton}
                />
            </button>
        </div>
    );
};

export default Counter;