import React from 'react';
import {Alert, Button, Form} from "react-bootstrap";
import classes from './InfoForm.module.css'
import {useNavigate} from "react-router-dom";
import {judgeSuccess} from "../funcTool";
import {useAddFacilitiesMutation} from "../../store/api/FacilitiesInfoApi";
import {useSetAllInfoMutation} from "../../store/api/InfoApi";

const InfoAllForm = () => {

    const [inputData, setInputData] = React.useState({
        context:""
    });

    const[add,addResult]=useSetAllInfoMutation();

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


                <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1"
                >
                    <Form.Label>广播内容</Form.Label>
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

export default InfoAllForm;