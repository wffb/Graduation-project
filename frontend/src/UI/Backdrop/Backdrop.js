import React from 'react';
import ReactDOM from 'react-dom';

import classes from './Backdrop.module.css'


const backdropRoot = document.getElementById('backdrop');

const Backdrop = (props) => {

    return ReactDOM.createPortal(
        <div
            className={`${classes.backdrop} ${props.className}`}
            onClick={props.onClick}
        >
            {props.children}
        </div>,
        backdropRoot
    )
};

export default Backdrop;