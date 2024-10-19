package org.example.bsproject.controller;

import org.example.bsproject.common.exception.WebSockertException;
import org.example.bsproject.component.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    WebSocket webSocket;

    public void testWb() throws WebSockertException {
            System.out.println("方法开始");
            webSocket.onOpen(null,null);
            System.out.println("方法结束");

    }
}
