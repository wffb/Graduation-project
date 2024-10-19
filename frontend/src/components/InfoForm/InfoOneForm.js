import React from 'react';
import {Alert, Button, Form} from "react-bootstrap";
import classes from "./InfoForm.module.css";
import {useNavigate, useNavigation} from "react-router-dom";
import {useSetAllInfoMutation, useSetOneInfoMutation} from "../../store/api/InfoApi";
import {judgeSuccess} from "../funcTool";

const InfoOneForm = () => {

    const nav = useNavigate();
    const [inputData, setInputData] = React.useState({
        username:"",
        context:""
    });
    const[add,addResult]=useSetOneInfoMutation();

    const usernameChangeHandler = (e)=>{
        setInputData((pre)=>{
            return {...pre,username:e.target.value};
        })
    }
    const contextChangeHandler = (e)=>{
        setInputData((pre)=>{
            return {...pre,context:e.target.value};
        })
    }
    const submitHandler = (e)=>{

        add(inputData).then((res)=>{

            if(judgeSuccess(res)){
                cancelHandler();}

        })
    }
    const cancelHandler = (e)=>{
        setInputData({
            username:"",
            context:""
        })

    }

    return (
        <div>
            {addResult.isError &&
                <Alert severity="error" dismissible >{addResult.data.message}</Alert>
            }
            {addResult.isSuccess &&
                <Alert severity="success" dismissible>{addResult.data.message}</Alert>
            }

            <Form className={classes.form}>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1"
                >
                    <Form.Label>目标用户名</Form.Label>
                    <Form.Control
                        type="text"
                        value={inputData.username}
                        onChange={usernameChangeHandler}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1"
                >
                    <Form.Label>私信内容</Form.Label>
                    <Form.Control
                        as="textarea" rows={4}
                        value={inputData.context}
                        onChange={contextChangeHandler}
                    />
                </Form.Group>


                <div className={classes.btns}>
                <Button variant="outline-secondary"
                    onClick={submitHandler}
                >提交</Button>
                <Button variant="outline-secondary"
                    onClick={cancelHandler}
                >取消</Button>
                </div>
            </Form>
        </div>
    );
};

export default InfoOneForm;