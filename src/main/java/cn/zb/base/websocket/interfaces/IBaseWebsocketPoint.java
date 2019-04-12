package cn.zb.base.websocket.interfaces;

import java.io.IOException;
import java.util.Set;

import javax.websocket.Session;

/**
 * 
 * @ClassName:  IBaseWebsocketPoint   
 * @Description:websocek接口的接口类  
 * @author: 陈军
 * @date:   2019年1月30日 上午9:58:58   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IBaseWebsocketPoint {

    /**
     * 
     * @Title: onMessage   
     * @Description: 接受信息 
     * @author:陈军
     * @date 2019年1月30日 上午9:28:24 
     * @param message
     * @param session      
     * void      
     * @throws
     */
    void onMessage(String message, Session session);

    void onClose();

    void onError(Throwable t);

    void sendMessage(String message) throws IOException;

    /**
     * 
     * @Title: sendBatchMessage   
     * @Description: 群发消息 
     * @author:陈军
     * @date 2019年1月30日 上午9:16:55 
     * @param message      
     * void      
     * @throws
     */
    void sendBatchMessage(String message, Set<IBaseWebsocketPoint> points);

    /**
     * 
     * @Title: getSession   
     * @Description: 获得当前连接的session  
     * @author:陈军
     * @date 2019年1月30日 上午9:27:34 
     * @return      
     * Session      
     * @throws
     */
    Session getSession();

    void onOpen(Session session);

}
