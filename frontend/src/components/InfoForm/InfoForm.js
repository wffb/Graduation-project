import React from 'react';
import {Tab, Tabs} from "react-bootstrap";
import InfoAllForm from "./InfoAllForm";
import InfoOneForm from "./InfoOneForm";

/**
 * 用来用户广播通知和单点发生信息
 * @returns {Element}
 * @constructor
 */
const InfoForm = () => {
    return (
        <div>
            <Tabs
                defaultActiveKey="all"
                id="uncontrolled-tab-example"
                variant="underline"
                className="mb-3"
                justify
            >
                <Tab eventKey="all" title="全局广播">
                    <InfoAllForm/>
                </Tab>
                <Tab eventKey="one" title="个人私信">
                    <InfoOneForm/>
                </Tab>

            </Tabs>

        </div>
    );
};

export default InfoForm;