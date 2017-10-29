package ar.edu.itba.ingesoft;

import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Created by traies on 10/23/2017.
 */

@Path("/")
@Singleton
public class ChatResource {
    private static final Logger logger = LoggerFactory.getLogger(ChatResource.class);

    private static ChatResource instance;

    private ChannelManager channelManager = ChannelManager.getInstance();



    /*@GET
    @Path("chat")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> chat(@QueryParam("sessionid") int sessionid) {
        return Optional.ofNullable(clients.get(sessionid))
                .map(x -> x.getMessages())
                .orElseThrow(IllegalArgumentException::new);
    }*/

    @POST
    @Path("sendmessage")
    @Produces(MediaType.APPLICATION_JSON)
    public String chat(@QueryParam("chatid") int chatid, @QueryParam("user") String user, @QueryParam("text") String text) {
        Subject<Message> ch = Optional.ofNullable(channelManager.getChannel(chatid)).orElseThrow(IllegalArgumentException::new);
        ch.onNext(new Message(user, text));
        return "ok";
    }

    public static ChatResource getChatResource(){
        return instance;
    }

    public Subject<Message> getChannel(int channelId){
        return Optional.ofNullable(channelManager.getChannel(channelId)).orElseThrow(IllegalArgumentException::new);
    }
}
