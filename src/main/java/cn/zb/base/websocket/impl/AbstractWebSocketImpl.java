package cn.zb.base.websocket.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zb.base.websocket.interfaces.IBaseWebsocketPoint;

/**
 * 
 * @ClassName:  AbstractWebSocketImpl   
 * @Description:websocket接口的抽象实现 
 * @author: 陈军
 * @date:   2019年1月30日 上午9:59:28   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public abstract class AbstractWebSocketImpl implements IBaseWebsocketPoint {

    private static Logger logger = LoggerFactory.getLogger(AbstractWebSocketImpl.class);
    
    protected final static Set<Session> sessions=new HashSet<>();

    protected Session session;

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void sendMessage(String message) throws IOException {
        getSession().getBasicRemote().sendText(message);
    }

    @Override
    public void sendBatchMessage(String message, Set<IBaseWebsocketPoint> points) {
        for (IBaseWebsocketPoint point : points) {
            try {
                point.sendMessage(message);
            } catch (IOException e) {
                logger.error("发送消息错误:{}", e.getMessage());
                point.onError(e);
            }
        }
    }
}
