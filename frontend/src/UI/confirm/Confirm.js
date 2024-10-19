import React from 'react';
import Backdrop from '../Backdrop/Backdrop'
import classes from './confirm.module.css';




const Confirm = (props) => {


    return (
        // 屏蔽之前的触发事件
        <div
            onClick={e=>e.stopPropagation()}
        >
        <Backdrop
            className={classes.confirmBackdrop}
            // onClick={noHandler}>
        >
            <div
                className={classes.confirm}
                onClick={e=>e.stopPropagation()}
            >
                <p className={classes.text}>{props.desc}</p>

                <div className={classes.makesure}>
                    <button className={classes.yes} onClick={props.yesHandler}>确认</button>
                    <button className={classes.no} onClick={props.noHandler}>取消</button>
                </div>
            </div>
        </Backdrop>
        </div>
    );
};

export default Confirm;