import React, {useEffect, useState} from 'react';

import {useWebSocket} from "ahooks";
import {Alert, Button} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {clearInfo, setInfo} from "../../store/reducer/InfoSlice";

const Info = (props) => {
    
    const [show,setShow] = useState(false);
    const [detail, setDetail] = useState(false);
    const auth = useSelector((state) => state.auth);

    //信息类型
        //true -- 广播
        //false -- 个人公告
    const info = useSelector((state) => state.info);
    const dispatch = useDispatch();

// useWebSocket钩子函数会在组件加载时自动连接websocket，在组件卸载时自动销毁
    useWebSocket(`ws://192.168.153.129:8080/ws/${auth.user.username}`, {



        // webSocket 连接成功回调
        onOpen: (event: WebSocketEventMap['open'], instance: WebSocket) => {

            // setShow(false);
            // setDetail(false);

            // 连接成功，将用户token发送给后端

        },
        // webSocket 收到消息回调
        onMessage: (message: WebSocketEventMap['message'], instance: WebSocket) =>{


            //装载信息
            console.log(message)

            const msg = JSON.parse(message.data);

            if(msg.username){
                dispatch(setInfo({
                    context:msg.data,
                    contextType:false
                }))
            }
            else{
                dispatch(setInfo({
                    context:msg.data,
                    contextType:true
                }))
            }
            // 这里可以执行自己的业务操作，例如：重新拉取数据刷新列表
            setShow(true);
        },
        // 设置失败重试次数
        reconnectLimit: 2
    });

    const closeHandler=()=>{
        setShow(false);
        dispatch(clearInfo());
    }
    const showHandler=()=>{
        setDetail(true);
    }


    return (
        <>
            {show &&
                <>
                    <Alert show={detail} variant="success" >
                        <Alert.Heading>{info.contextType?"广播消息":"管理员私信"}</Alert.Heading>
                        <p>
                            {info.context}
                        </p>
                        <hr />
                        <div className="d-flex justify-content-end">
                            <Button onClick={() => setDetail(false)} variant="outline-success">
                                Close me
                            </Button>
                        </div>
                    </Alert>

                    {!detail &&
                        <Alert onClose={closeHandler} dismissible >
                            <Alert.Link href="#" onClick={showHandler}>您收到一条新的消息，点此查看</Alert.Link>
                        </Alert >}
                </>
            }
        </>
    );
};

export default Info;