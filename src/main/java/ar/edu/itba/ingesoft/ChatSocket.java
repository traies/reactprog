package ar.edu.itba.ingesoft;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by traie_000 on 10/27/2017.
 */
@WebSocket
public class ChatSocket {
    private static Logger logger = LoggerFactory.getLogger(ChatSocket.class);

    @OnWebSocketConnect
    public void onConnect(Session sess) {
        logger.debug("hello");
    }
}
