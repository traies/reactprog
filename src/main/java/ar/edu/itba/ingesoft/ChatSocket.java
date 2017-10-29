package ar.edu.itba.ingesoft;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
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
    public void onConnect(Session session) {
        String  paramsPart = session.getUpgradeRequest().getRequestURI().toString().split("\\?")[1];
        String [] params = paramsPart.split("&");
        Integer chatId = new Integer(params[0].split("=")[1]);
        String username = params[1].split("=")[1];
        UserClient user = new UserClient(session,username,chatId);
        ChannelManager.getInstance().getChannel(chatId).subscribe(user);
        ChannelManager.getInstance().getChannel(chatId).onNext(new Message(username, "Entered Chat"));
        ChannelManager.getInstance().getClients().put(session,user);
        ChannelManager.getInstance().getClientsChannels().put(session,chatId);

    }

    @OnWebSocketClose
    public void onClose(Session session,int a , String b){
        UserClient user = ChannelManager.getInstance().getClients().get(session);
        ChannelManager.getInstance().getChannel(user.getChatId()).onNext(new Message(user.getUsername(), "Left Chat"));
        ChannelManager.getInstance().getClients().get(session).getDisposable().dispose();
        ChannelManager.getInstance().getClientsChannels().remove(session);
        ChannelManager.getInstance().getClients().remove(session);

    }
}
