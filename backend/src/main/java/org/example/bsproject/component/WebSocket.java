package org.example.bsproject.component;

import cn.hutool.json.JSONObject;
import org.example.bsproject.common.exception.WebSockertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/ws/{username}")

public class WebSocket {
    //保存连接对象
    private Session session;
    private String username;

    //静态保存连接对象
    private  static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    //保存对应的名称-session
    private static Map<String,Session> sessionPool = new HashMap<>();

    //日志记录
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username")String username) throws WebSockertException{

        this.session=session;
        this.username=username;

        webSockets.add(this);

        if(username==null)
            throw  new WebSockertException("连接用户名出现错误：不能为空");

        sessionPool.put(username, session);

        LOGGER.info(username + "[websocket消息]有新的连接，总数为:" + webSockets.size());
    }

    @OnClose
    public void onClose() {
        //移除当前记录
        webSockets.remove(this);
        sessionPool.remove(this.username);

        LOGGER.info("[websocket消息]连接断开，总数为:" + webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        LOGGER.info("[websocket消息]收到客户端消息:"+message);
    }

    // 此为广播消息
    public void sendAllMessage(String message,Object data) {

        JSONObject result=new JSONObject();
        Map<String,Object>map = new HashMap<>();
        map.put("message",message);
        map.put("data",data);
        result.putAll(map);

        for(WebSocket webSocket : webSockets) {
            LOGGER.info("[websocket消息]广播消息:"+ data.toString());
            try {
                webSocket.session.getAsyncRemote().sendText(result.toString());
            } catch (Exception e) {
                LOGGER.error("对"+webSocket.username+"进行广播时失败");
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String username, String message,Object data) {
        JSONObject result=new JSONObject();

        //标识点对点发送
        result.putOnce("username",username);
        result.putOnce("message",message);
        result.putOnce("data",data);

        LOGGER.info("[websocket消息]单点消息:"+data.toString()+" 对象用户名:"+username);
        Session session = sessionPool.get(username);

        if (session != null) {
            try {
                session.getAsyncRemote().sendText(result.toString());
            } catch (Exception e) {
                LOGGER.error("对"+username+"进行广播时失败");
            }
        }
    }

}
