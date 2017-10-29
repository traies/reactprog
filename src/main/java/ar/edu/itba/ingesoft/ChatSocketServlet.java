package ar.edu.itba.ingesoft;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by traie_000 on 10/27/2017.
 */
@WebServlet(urlPatterns = {"/socket"})
public class ChatSocketServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        System.out.println("entro3");
        webSocketServletFactory.register(ChatSocket.class);
    }

}
