package ar.edu.itba.ingesoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by traie_000 on 10/24/2017.
 */
@ServerEndpoint("/socket")
public class ChatWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocket.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Session abierta");
    }
}
